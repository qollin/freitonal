(ns de.cr.freitonal.server.insert-test
  (:use [de.cr.freitonal.server.insert])
  (:use [de.cr.freitonal.server.testtools])
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.server.javainterop])
  
  (:use [clojure.test])
  (:use [clojure.contrib.sql :as sql :only ()])
  
  (:import (de.cr.freitonal.shared.models VolatileInstrumentation Instrumentation VolatileItem Item)))

(deftest test-insert-instrumentation []
  (dbtest "test that two times the same instrument becomes one entry in the DB"
    (let [violin (insert-instrument (VolatileItem. "Violin"))
          instrumentation (insert-instrumentation (VolatileInstrumentation. "nickname" (create-ArrayList violin violin)))]
      (sql/with-query-results res
        ["SELECT * FROM classical_instrumentationmember WHERE instrumentation_id = ?" (.getID instrumentation)]
        (is (= 1 (count res)))
        (is (= 2 (:numberofinstruments (first res))))
        (is (= (.getID violin) (str (:instrument_id (first res))))))))
  
  (dbtest "test that the order of the instruments in the given instrumentation is honored"
    (let [violin (insert-instrument (VolatileItem. "Violin"))
          piano (insert-instrument (VolatileItem. "Piano"))
          instrumentation (insert-instrumentation (VolatileInstrumentation. "nickname" (create-ArrayList violin piano)))]
      (sql/with-query-results res
        ["SELECT * FROM classical_instrumentationmember WHERE instrumentation_id = ? ORDER BY ordinal" 
         (.getID instrumentation)]
        (is (= 2 (count res)))
        (is (.getID violin) (str (:instrument_id (first res))))
        (is (.getID piano) (str (:instrument_id (nth res 1))))))))

(deftest test-insert-piecetype []
  (dbtest "test the insertion of a piece type"
    (let [piecetype (insert-piecetype (VolatileItem. "Sonate"))]
      (sql/with-query-results res
        ["SELECT * FROM classical_piecetype WHERE id = ?" (.getID piecetype)]
        (is (= 1 (count res)))
        (is (= (.getValue piecetype) (:name (first res))))))))

