(ns de.cr.freitonal.server.insert
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.server.render])

  (:use [clojure.contrib.sql :as sql :only ()])
  
  (:import (de.cr.freitonal.shared.models VolatileInstrumentation Instrumentation 
                                          VolatileItem Item 
                                          VolatilePiece Piece
                                          VolatilePiecePlusInstrumentationType PiecePlusInstrumentationType
                                          VolatileCatalog Catalog)))

(defn insert-simple-object [table object]
  (sql/insert-records table object)
  (sql/with-query-results res 
    ["SELECT LAST_INSERT_ID() as id"]
    (:id (first res))))

(defmulti insert-instrument class)
(defmethod #^Item insert-instrument String [instrument-name]
  (let [instrument (VolatileItem. instrument-name)
        id (insert-simple-object :classical_instrument {:name (.getValue instrument)})]
    (Item. (str id) instrument)))
(defmethod #^Item insert-instrument VolatileItem [instrument]
  (insert-instrument (.getValue instrument)))

(defn doCreateInstrument [conf-file #^VolatileItem instrument]
  (sql/with-connection (load-file conf-file)
    (insert-instrument instrument)))

(defn insert-instrumentation* [nickname]
  (insert-simple-object :classical_instrumentation {:nickname nickname}))

(defn insert-instrumentation 
  ([#^String nickname & instruments]
    (insert-instrumentation (VolatileInstrumentation. nickname (into-array instruments))))
  ([#^VolatileInstrumentation instrumentation]
    (let [id (insert-instrumentation* (.getNickname instrumentation))
          originalInstrumentList (seq (.getInstruments instrumentation))
          instrumentCount (create-countmap originalInstrumentList)]
      (loop [instruments (remove-duplicates originalInstrumentList)
             counter 1]
        (when (not (empty? instruments))
          (let [instrument (first instruments)]
            (sql/insert-records :classical_instrumentationmember {:instrument_id (.getID instrument) 
                                                                  :instrumentation_id id
                                                                  :numberofinstruments (get instrumentCount instrument)
                                                                  :ordinal counter})
            (recur (rest instruments) (inc counter)))))
      (Instrumentation. (str id) instrumentation))))

(defn doCreateInstrumentation [conf-file #^VolatileInstrumentation instrumentation]
  (sql/with-connection (load-file conf-file)
    (insert-instrumentation instrumentation)))

(defn insert-composer [#^VolatileItem composer]
  (let [id (insert-simple-object :classical_composer {:first_name "" :middle_name "" :last_name (.getValue composer)})]
    (Item. (str id) composer)))

(defn doCreateComposer [conf-file #^VolatileItem composer]
  (sql/with-connection (load-file conf-file)
    (insert-composer composer)))

(defn insert-piecetype [#^VolatileItem piecetype]
  (let [id (insert-simple-object :classical_piecetype {:name (.getValue piecetype)})]
    (Item. (str id) piecetype)))

(defn doCreatePieceType [conf-file #^VolatileItem piecetype]
  (sql/with-connection (load-file conf-file)
    (insert-piecetype piecetype)))

(defmulti insert-catalogname class)
(defmethod #^Item insert-catalogname String [catalogname]
  (let [id (insert-simple-object :classical_catalogtype {:name catalogname})]
    (Item. (str id) catalogname)))
(defmethod #^Item insert-catalogname VolatileItem [catalog]
  (insert-catalogname (.getValue catalog)))

(defn doCreateCatalogName [conf-file #^VolatileItem catalogName]
  (sql/with-connection (load-file conf-file)
    (insert-catalogname catalogName)))

(defmulti insert-catalog (fn [param & _] (class param)))
(defmethod #^Catalog insert-catalog VolatileItem [catalogname ordinal sub-ordinal]
  (let [id (insert-simple-object :classical_catalog {:name_id (.getID catalogname) :ordinal ordinal :sub_ordinal sub-ordinal})]
    (Catalog. (str id) catalogname (create-string-from-ordinal-and-subordinal ordinal sub-ordinal))))
(defmethod #^Catalog insert-catalog VolatileCatalog [catalog]
  (let [[ordinal sub-ordinal] (create-ordinal-and-subordinal-from-string (.getOrdinal catalog))]
    (insert-catalog (.getCatalogName catalog) ordinal sub-ordinal)))

(defn doCreateCatalog [conf-file #^VolatileCatalog catalog]
  (sql/with-connection (load-file conf-file)
    (insert-catalog catalog)))

(defn create-structure-for-volatile-piece [#^VolatilePiece piece]
  (let [mandatoryStructure {:composer_id (.getID (.getComposer piece))}
        struct1 (if (not (nil? (.getPieceType piece)))
                  (assoc mandatoryStructure :piece_type_id (.getID (.getPieceType piece)))
                  mandatoryStructure)
        struct2 (if (not (nil? (.getCatalog piece)))
                  (assoc struct1 :catalog_id (.getID (.getCatalog piece)))
                  struct1)
        struct3 (if (not (nil? (.getParent piece)))
                  (assoc struct2 :parent_id (.getID (.getParent piece)))
                  struct2)]
        struct3))

(defn #^Piece insert-piece [#^VolatilePiece piece]
  (let [id (insert-simple-object :classical_piece (create-structure-for-volatile-piece piece))]
    (sql/insert-records :classical_piece_instrumentations {:piece_id id
                                                           :instrumentation_id (.getID (.getInstrumentationAsNonVolatile piece))})
    (Piece. (str id) piece)))

(defn insert-piece+instrumentation-type [#^VolatilePiecePlusInstrumentationType piece+instrumentation-type]
  (let [id (insert-simple-object :classical_typeplusinstrumentation {:type_id (.getID (.getPieceType piece+instrumentation-type))
                                                                     :instrumentation_id (.getID (.getInstrumentation piece+instrumentation-type))
                                                                     :nickname (.getNickname piece+instrumentation-type)})]
    (PiecePlusInstrumentationType. (str id) piece+instrumentation-type)))
