(ns de.cr.freitonal.unittests.server.insert-test
  (:use [de.cr.freitonal.server.insert])
  (:use [de.cr.freitonal.unittests.server.testtools])
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.server.javainterop])
  (:use [de.cr.freitonal.server.tables :as table :only ()])
  
  (:use [clojure.test])
  (:use [clojure.contrib.sql :as sql :only ()])
  
  (:use clojureql.core)
  
  (:import (de.cr.freitonal.shared.models 
             VolatileInstrumentation 
             Instrumentation 
             VolatileItem 
             Item 
             VolatilePiece 
             VolatileCatalog
             Catalog)))

(deftest test-insert-instrumentation []
  (dbtest "test that two times the same instrument becomes one entry in the DB"
    (let [instrumentation (insert-instrumentation "nickname" violin violin)
          res @(select table/instrumentationmember (where (= :instrumentation_id (.getID instrumentation))))]
        (is (= 1 (count res)))
        (is (= 2 (:numberofinstruments (first res))))
        (is (= (.getID violin) (str (:instrument_id (first res)))))))
  
  (dbtest "test that the order of the instruments in the given instrumentation is honored"
    (let [instrumentation (insert-instrumentation "nickname" violin piano)
          res @(-> (select table/instrumentationmember (where (= :instrumentation_id (.getID instrumentation))))
                 (sort [:ordinal]))]
        (is (= 2 (count res)))
        (is (= (.getID violin) (str (:instrument_id (first res)))))
        (is (= (.getID piano) (str (:instrument_id (nth res 1))))))))

(deftest test-insert-piecetype []
  (dbtest "test the insertion of a piece type"
    (let [piecetype (insert-piecetype (VolatileItem. "Sonate"))
          res @(select table/piecetype (where (= :id (.getID piecetype))))]
        (is (= 1 (count res)))
        (is (= (.getValue piecetype) (:name (first res)))))))

(deftest test-insert-catalogname []
  (dbtest "test the insertion of a catalog name"
    (let [catalogname (insert-catalogname "Opus")
          res @(select table/catalogname (where (= :id (.getID catalogname))))]
      (is (= 1 (count res)))
      (is (= (.getValue catalogname) (:name (first res)))))))

(deftest test-insert-catalog []
  (dbtest "test the insertion of a catalog"
    (let [catalogname (insert-catalogname "Opus")
          catalog (insert-catalog catalogname "1" "a")
          res @(select table/catalog (where (= :id (.getID catalog))))]
      (is (= 1 (count res)))
      (is (= (.getOrdinal catalog) "1-a")))))

(deftest test-create-catalog-from-volatile-catalog []
  (dbtest "check that an existing catalog is returned from the DB"
    (let [vcatalog (VolatileCatalog. opus "27-1")
          catalog (create-catalog-from-volatile-catalog vcatalog)]
      (is (= opus27-1 catalog))))
  (dbtest "check that a non existing catalog is created and returned"
    (let [vcatalog (VolatileCatalog. opus "123-4")
          catalog (create-catalog-from-volatile-catalog vcatalog)
          res @(select table/catalog (where (= :id (.getID catalog))))]
      (is (= 1 (count res))))))
    

(deftest test-insert-piece []
  (dbtest "test insert a piece with a parent"
    (let [parent-piece (insert-piece (VolatilePiece. beethoven piano-solo opus27-1))
          piece (insert-piece (VolatilePiece. mozart piano-solo opus27-1 parent-piece))
          res @(select table/piece (where (= :id (.getID piece))))]
      (is (= (.getID parent-piece) (str (:parent_id (first res))))))))

(deftest test-insert-piece-with-volatile-catalog []
  (dbtest ""
    (let [volatile-catalog (VolatileCatalog. opus "66-3")
          piece (insert-piece (VolatilePiece. mozart piano-solo volatile-catalog))
          res @(select table/piece (where (= :id (.getID piece))))]
      (is (= (.getID piece) (str (:id (first res))))))))

(deftest test-insert-piece-with-volatiles []
  (dbtest ""
    (let [volatile-violin-solo (VolatileInstrumentation. "solo violin" (create-ArrayList violin))
          volatile-catalog (VolatileCatalog. opus "27-2")
          piece (insert-piece (VolatilePiece. mozart volatile-violin-solo volatile-catalog))
          res @(select table/piece (where (= :id (.getID piece))))]
      (is (.hasNonVolatileInstrumentation piece))
      (is (.hasNonVolatileCatalog piece))
      (is (= (.getID piece) (str (:id (first res))))))))

(deftest test-create-structure-for-volatile-piece []
  (dbtest ""
    (is (= {:composer_id (.getID mozart) :piece_type_id (.getID sonata)}
          (create-structure-for-volatile-piece (VolatilePiece. mozart nil sonata))))
    (is (= {:composer_id (.getID mozart)}
          (create-structure-for-volatile-piece (VolatilePiece. mozart nil))))
    (is (= {:composer_id (.getID mozart) :catalog_id (.getID opus27-1)}
          (create-structure-for-volatile-piece (VolatilePiece. mozart nil opus27-1))))))


(comment defn test-ns-hook []
  (test-create-catalog-from-volatile-catalog))


