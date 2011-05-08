(ns de.cr.freitonal.server.tables
  (:use [de.cr.freitonal.server.tools])
  
  (:use [clojureql.core :as q :only ()]))

(defn -define-tables [db]
  (def instrumentationmember (q/table db "classical_instrumentationmember"))
  (def piecetype (q/table db "classical_piecetype"))
  (def catalogname (q/table db "classical_catalogtype"))
  (def catalog (q/table db "classical_catalog"))
  (def piece (q/table db "classical_piece"))
  (def musickey (q/table db "classical_musickey")))

(def define-tables (run-once -define-tables))