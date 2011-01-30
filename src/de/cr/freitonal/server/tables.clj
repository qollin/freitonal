(ns de.cr.freitonal.server.tables
  (:use [de.cr.freitonal.server.tools])
  
  (:use clojureql.core))

(defn -define-tables [db]
  (def instrumentationmember (table db "classical_instrumentationmember"))
  (def piecetype (table db "classical_piecetype"))
  (def catalogname (table db "classical_catalogtype"))
  (def catalog (table db "classical_catalog"))
  (def piece (table db "classical_piece")))

(def define-tables (run-once -define-tables))