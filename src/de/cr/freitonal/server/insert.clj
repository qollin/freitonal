(ns de.cr.freitonal.server.insert
  (:use [de.cr.freitonal.server.tools])
  (:use [clojure.contrib.sql :as sql :only ()])
  
  (:import (de.cr.freitonal.shared.models VolatileInstrumentation Instrumentation 
                                          VolatileItem Item 
                                          VolatilePiece Piece
                                          VolatilePiecePlusInstrumentationType PiecePlusInstrumentationType)))

(defn insert-simple-object [table object]
  (sql/insert-records table object)
  (sql/with-query-results res 
    ["SELECT LAST_INSERT_ID() as id"]
    (:id (first res))))

(defn insert-instrument [#^VolatileItem instrument]
  (let [id (insert-simple-object :classical_instrument {:name (.getValue instrument)})]
    (Item. (str id) instrument)))

(defn insert-instrumentation* [nickname]
  (insert-simple-object :classical_instrumentation {:nickname nickname}))

(defn insert-instrumentation [#^VolatileInstrumentation instrumentation]
  (let [id (insert-instrumentation* (.getNickname instrumentation))
        instrumentCount (create-countmap (seq (.getInstruments instrumentation)))]
    (loop [instruments (keys instrumentCount)
           counter 1]
      (when (not (empty? instruments))
        (let [instrument (first instruments)]
          (sql/insert-records :classical_instrumentationmember {:instrument_id (.getID instrument) 
                                                                :instrumentation_id id
                                                                :numberofinstruments (get instrumentCount instrument)
                                                                :ordinal counter})
          (recur (rest instruments) (inc counter)))))
    (Instrumentation. (str id) instrumentation)))

(defn insert-composer [#^VolatileItem composer]
  (let [id (insert-simple-object :classical_composer {:first_name "" :middle_name "" :last_name (.getValue composer)})]
    (Item. (str id) composer)))

(defn insert-piecetype [#^VolatileItem piecetype]
  (let [id (insert-simple-object :classical_piecetype {:name (.getValue piecetype)})]
    (Item. (str id) piecetype)))

(defn insert-piece [#^VolatilePiece piece]
  (let [id (insert-simple-object :classical_piece {:composer_id (.getID (.getComposer piece))
                                                   :piece_type_id (.getID (.getPieceType piece))})]
    (sql/insert-records :classical_piece_instrumentations {:piece_id id
                                                           :instrumentation_id (.getID (.getInstrumentationAsNonVolatile piece))})
    (Piece. (str id) piece)))

(defn insert-piece+instrumentation-type [#^VolatilePiecePlusInstrumentationType piece+instrumentation-type]
  (let [id (insert-simple-object :classical_typeplusinstrumentation {:type_id (.getID (.getPieceType piece+instrumentation-type))
                                                                     :instrumentation_id (.getID (.getInstrumentation piece+instrumentation-type))
                                                                     :nickname (.getNickname piece+instrumentation-type)})]
    (PiecePlusInstrumentationType. (str id) piece+instrumentation-type)))
