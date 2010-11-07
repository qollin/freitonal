(ns de.cr.freitonal.server.search-test
  (:use [de.cr.freitonal.server.search])
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.server.testtools])
  (:use [de.cr.freitonal.server.insert])
  (:use [de.cr.freitonal.server.javainterop])
  
  (:use [clojure.contrib.sql :as sql :only ()])
  (:use [clojure.contrib.json :only (read-json)])
  (:use [clojure.contrib.json :only (print-json)])
  (:use	[clojure.contrib.duck-streams :only (slurp*)])
  (:use [clojure.test])
  (:use [clojure.set])

  (:import (de.cr.freitonal.shared.models VolatilePiece 
                                          VolatileItem 
                                          VolatileInstrumentation
                                          VolatilePiecePlusInstrumentationType)))

(deftest test-add-search-clause []
  (is (= {:where " AND piece.composer_id IN (?, ?)"
          :from ["classical_piece piece"]
          :values ["1", "2"]}
        (add-search-clause ["piece-composer" ["1", "2"]])))
  (is (= {:where " AND piece.catalog_id IN (?)" 
          :from ["classical_piece piece"]
          :values ["110"]}
        (add-search-clause ["piece-catalog" ["110"]]))))

(deftest test-combine-search-clauses []
  (is (= {:from ["a" "b"]
          :where "a.a = ? AND b.b = ? AND b.c = ?"
          :values ["1" "2" "3"]}
        (combine-search-clauses '({:from ["a"]
                                   :where "a.a = ?"
                                   :values ["1"]}
                                   {:from  ["a" "b"]
                                    :where " AND b.b = ? AND b.c = ?"
                                    :values ["2" "3"]}))))
  (is (= {:from ["a" "b"]
          :where "a.a = ? AND NO values"
          :values ["1"]}
        (combine-search-clauses '({:from ["a"]
                                   :where "a.a = ?"
                                   :values ["1"]}
                                   {:from  ["a" "b"]
                                    :where " AND NO values"})))))

(deftest test-add-search-clauses []
  (is (= {:select "a.c1, b.c2"
           :from ["a" "b" "classical_piece piece"]
           :where "a.b = b.id AND piece.composer_id IN (?)"
           :values ["1"]}
        (add-search-clauses 
          {:select "a.c1, b.c2"
           :from ["a" "b"]
           :where "a.b = b.id"}
           {"piece-composer" ["1"]})))
  (is (= {:select "a.c1, b.c2"
           :from ["a" "b" "classical_piece piece"]
           :where "a.b = b.id AND piece.composer_id IN (?, ?)"
           :values ["1", "2"]}
        (add-search-clauses 
          {:select "a.c1, b.c2"
           :from ["a" "b"]
           :where "a.b = b.id"}
           {"piece-composer" ["1", "2"]}))))

(deftest search-for-violin-and-violin []
  (dbtest "test that searching for two violins does not find an instrumentation with one violin"
    (let [violin (insert-instrument (VolatileItem. "Violin"))
          
          ;insert a piece that should not be found (it only includes _one_ violin):
          composerA (insert-composer (VolatileItem. "Alkan"))
          instrumentationA (insert-instrumentation (VolatileInstrumentation. "one violin" (create-ArrayList violin)))
          pieceA (insert-piece (VolatilePiece. composerA instrumentationA))
          
          ;insert the piece that should be found (it includes two violins):
          composerB (insert-composer (VolatileItem. "Bartok"))
          instrumentationB (insert-instrumentation (VolatileInstrumentation. "two violins" (create-ArrayList violin violin)))
          pieceB (insert-piece (VolatilePiece. composerB instrumentationB))
          
          ;do the search:
          searchResult (read-json (search {"piece-instrumentations__instrument" [(.getID violin), (.getID violin)]}) false)]
      (is (= 1 (count (searchResult "piece-composer"))))
      (is (= (.getID composerB) (str (first (first (searchResult "piece-composer")))))))))

(deftest check-that-search-returns-piece+instrumentation-types []
  (dbtest "test that a full search returns stuff from the piece plus instrumentation types table"
    (let [violin (insert-instrument (VolatileItem. "Violin"))
          composer (insert-composer (VolatileItem. "Mozart"))
          instrumentation (insert-instrumentation (VolatileInstrumentation. "one violin" (create-ArrayList violin)))
          piecetype (insert-piecetype (VolatileItem. "Sonate"))
          piece (insert-piece (VolatilePiece. composer instrumentation piecetype))
          piece+instrumentation-type (insert-piece+instrumentation-type (VolatilePiecePlusInstrumentationType. "Violinsonate" piecetype instrumentation))
          searchResult (read-json (search {}) false)]
      (is (= 1 (count (searchResult "piece-type+instrumentation"))))
      (is (= (.getID piece+instrumentation-type) (str (first (first (searchResult "piece-type+instrumentation")))))))))

(deftest check-that-instrumentations-are-returned-in-the-right-format []
  (dbtest ""
    (let [violin (insert-instrument (VolatileItem. "Violin"))
          piano (insert-instrument (VolatileItem. "Piano"))
          composer (insert-composer (VolatileItem. "Mozart"))
          instrumentation (insert-instrumentation (VolatileInstrumentation. "violin piano combo" (create-ArrayList violin piano)))
          piece (insert-piece (VolatilePiece. composer instrumentation))
          jsonResult (search {})
          searchResult (read-json jsonResult false)]
      (is (= 1 (count (searchResult "piece-instrumentations"))))
      (is (= (.getID instrumentation) (str (get (first (searchResult "piece-instrumentations")) "id"))))
      (is (= [[(.getID violin) (.getValue violin)] [(.getID piano) (.getValue piano)]] 
            (map #(assoc % 0 (str (first %))) (get (first (searchResult "piece-instrumentations")) "instruments")))))))

(deftest test-merge-instrumentRecords-into-instrumentations []
  (let [mergedRecords (merge-instrumentRecords-into-instrumentations [{:id 1 :nickname "bla" :instruments [[5 "piano"]]},
                                                                      {:id 1 :nickname "bla" :instruments [[7 "violin"]]}, 
                                                                      {:id 2 :nickname "blub" :instruments [[6 "viola"]]}])]
    (is (= 2 (count mergedRecords)))
    (is (= 1 (:id (first mergedRecords))))
    (is (= [[5 "piano"] [7 "violin"]] (:instruments (first mergedRecords))))))

(comment defn test-ns-hook []
  (search-for-violin-and-violin))
