(ns de.cr.freitonal.server.testtools
  (:use [de.cr.freitonal.server.tools])
  (:use [clojure.test])
  (:use [clojure.contrib.java-utils :only (read-properties)])
  (:use [clojure.contrib.sql :as sql :only ()])
  
  (:import
   (clojure.lang RT)
   (java.sql DriverManager)))

(defmacro tables []
  ''(classical_allocatedinstrument
      classical_allocatedinstrument_performers 
      classical_allocatedinstrumentation
      classical_allocatedinstrumentation_allocated_instruments 
      classical_catalog 
      classical_catalogtype 
      classical_composer 
      classical_instrument 
      classical_instrument_instrument_roles 
      classical_instrumentation 
      classical_instrumentation_instrument 
      classical_instrumentation_instruments 
      classical_instrumentationmember 
      classical_instrumentrole 
      classical_musickey 
      classical_operarole 
      classical_performance 
      classical_performer 
      classical_piece
      classical_piece_instrumentations 
      classical_piece_operaRoles
      classical_piece_opera_roles 
      classical_piece_parts 
      classical_piecetype
      classical_recording 
      classical_recording_performances 
      classical_recordlabel 
      classical_recordtype 
      classical_synonym 
      classical_typeplusinstrumentation))

(defn delete-all-tables []
  (apply sql/do-commands (map #(str "DELETE FROM " %) (tables))))

(defmacro dbtest [description & body]
  `(sql/with-connection (load-file "conf/db-empty.clj")
     (delete-all-tables)
    ~@body))

(defn runTests [package]
  "given a clojure package as a String it runs all tests in this package"
  ;(sql/with-connection (load-file "conf/db.clj")
    (let [summary (run-tests (symbol package))]
      (zipmap (map #(str %) (keys summary)) (vals summary))))
