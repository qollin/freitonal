(ns de.cr.freitonal.unittests.server.insert-test
  (:use [de.cr.freitonal.server.insert])
  (:use [de.cr.freitonal.unittests.server.testtools])
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.server.javainterop])
  
  (:use [clojure.test])
  (:use [clojure.contrib.sql :as sql :only ()])
  
  (:use clojureql.core)
  
  (:import (de.cr.freitonal.shared.models 
             VolatileInstrumentation 
             Instrumentation 
             VolatileItem 
             Item 
             VolatilePiece 
             Catalog)))

(def table-instrumentationmember (table db "classical_instrumentationmember"))
(def table-piecetype (table db "classical_piecetype"))
(def table-catalogname (table db "classical_catalogtype"))
(def table-catalog (table db "classical_catalog"))
(def table-piece (table db "classical_piece"))

(deftest test-insert-instrumentation []
  (dbtest "test that two times the same instrument becomes one entry in the DB"
    (let [instrumentation (insert-instrumentation "nickname" violin violin)
          res @(select table-instrumentationmember (where (= :instrumentation_id (.getID instrumentation))))]
        (is (= 1 (count res)))
        (is (= 2 (:numberofinstruments (first res))))
        (is (= (.getID violin) (str (:instrument_id (first res)))))))
  
  (dbtest "test that the order of the instruments in the given instrumentation is honored"
    (let [instrumentation (insert-instrumentation "nickname" violin piano)
          res @(-> (select table-instrumentationmember (where (= :instrumentation_id (.getID instrumentation))))
                 (sort [:ordinal]))]
        (is (= 2 (count res)))
        (is (= (.getID violin) (str (:instrument_id (first res)))))
        (is (= (.getID piano) (str (:instrument_id (nth res 1))))))))

(deftest test-insert-piecetype []
  (dbtest "test the insertion of a piece type"
    (let [piecetype (insert-piecetype (VolatileItem. "Sonate"))
          res @(select table-piecetype (where (= :id (.getID piecetype))))]
        (is (= 1 (count res)))
        (is (= (.getValue piecetype) (:name (first res)))))))

(deftest test-insert-catalogname []
  (dbtest "test the insertion of a catalog name"
    (let [catalogname (insert-catalogname "Opus")
          res @(select table-catalogname (where (= :id (.getID catalogname))))]
      (is (= 1 (count res)))
      (is (= (.getValue catalogname) (:name (first res)))))))

(deftest test-insert-catalog []
  (dbtest "test the insertion of a catalog"
    (let [catalogname (insert-catalogname "Opus")
          catalog (insert-catalog catalogname "1" "a")
          res @(select table-catalog (where (= :id (.getID catalog))))]
      (is (= 1 (count res)))
      (is (= (.getOrdinal catalog) "1-a")))))

(deftest test-insert-piece []
  (dbtest "test insert a piece with a parent"
    (let [parent-piece (insert-piece (VolatilePiece. beethoven piano-solo opus27-1))
          piece (insert-piece (VolatilePiece. mozart piano-solo opus27-1 parent-piece))
          res @(select table-piece (where (= :id (.getID piece))))]
      (is (= (.getID parent-piece) (str (:parent_id (first res))))))))

(deftest test-create-structure-for-volatile-piece []
  (is (= {:composer_id "1" :piece_type_id "2"}
        (create-structure-for-volatile-piece (VolatilePiece. (Item. "1" "Mozart") nil (Item. "2" "Sonate")))))
  (is (= {:composer_id "1"}
        (create-structure-for-volatile-piece (VolatilePiece. (Item. "1" "Mozart") nil))))
  (is (= {:composer_id "1" :catalog_id "2"}
        (create-structure-for-volatile-piece (VolatilePiece. (Item. "1" "Mozart") nil (Catalog. "2" (Item. "1" "Opus") ""))))))