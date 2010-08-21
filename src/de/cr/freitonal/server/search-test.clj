(ns de.cr.freitonal.server.search-test
  (:use [de.cr.freitonal.server.search])
  (:use [de.cr.freitonal.server.testtools])
  (:use [de.cr.freitonal.server.insert])
  (:use [de.cr.freitonal.server.javainterop])
  
  (:use [clojure.contrib.sql :as sql :only ()])
  (:use [clojure.contrib.json.read :only (read-json)])
  (:use [clojure.contrib.json.write :only (print-json)])
  (:use	[clojure.contrib.duck-streams :only (slurp*)])
  (:use [clojure.test])
  (:use [clojure.set])

  (:import (de.cr.freitonal.shared.models VolatilePiece 
                                          VolatileItem 
                                          VolatileInstrumentation
                                          VolatilePiecePlusInstrumentationType)))

(defn db-run [fun]
  (sql/with-connection (load-file "conf/db-test.clj") 
    (let [result (fun)]
      result)))

(defn search-and-verify-results [searchParams resultFile]
  (let [full-search-json (read-json (slurp* (str "test/de/cr/freitonal/unittests/client/test/data/" resultFile ".json")))
        search-result (read-json (search searchParams))]
    (doseq [key (filter #(not (or (= % "piece") (= % "recording") (= % "performance"))) (keys full-search-json))]
      (is (= (count (full-search-json key)) (count (search-result key))) 
        (str "comparison for " key " failed: different lengths"))
      (is (= (set (full-search-json key)) (set (search-result key)))
        (str "comparison for " key " failed: sets are not equal: " (difference (set (full-search-json key)) (set (search-result key))))))))

(deftest test-add-search-clause []
  (is (= {:where " AND piece.composer_id IN (?, ?)"
          :from ["classical_piece piece"]
          :values ["1", "2"]}
        (add-search-clause ["piece-composer" ["1", "2"]]))))

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

(deftest full-search []
  (search-and-verify-results {} "fullSearch"))

(deftest search-for-beethoven []
  (search-and-verify-results {"piece-composer" ["1"]} "searchForBeethoven"))

(deftest search-for-piano []
  (search-and-verify-results {"piece-instrumentations__instrument" ["4"]} "searchForPiano"))

(deftest search-for-quartett []
  (search-and-verify-results {"piece-piece_type" ["1"]} "searchForQuartett"))

(deftest search-for-opus []
  (search-and-verify-results {"piece-catalog__name" ["1"]} "searchForOpus"))

(deftest search-for-opus-10-2 []
  (search-and-verify-results {"piece-catalog__name" ["1"] "piece-catalog__number" ["85"]} "searchForOpus10-2"))

(deftest search-for-eroica []
  (search-and-verify-results {"piece-subtitle" ["Eroica"]} "searchForEroica"))

(deftest search-for-ordinal4a []
  (search-and-verify-results {"piece-type_ordinal" ["4a"]} "searchForOrdinal4a"))

(deftest search-for-amajor []
  (search-and-verify-results {"piece-music_key" ["31"]} "searchForAMajor"))

(deftest search-for-piano-and-violin []
  (search-and-verify-results {"piece-instrumentations__instrument" ["4", "1"]} "searchForPianoAndViolin"))

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
          searchResult (read-json (search {"piece-instrumentations__instrument" [(.getID violin), (.getID violin)]}))]
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
          searchResult (read-json (search {}))]
      (is (= 1 (count (searchResult "piece-type+instrumentation"))))
      (is (= (.getID piece+instrumentation-type) (str (first (first (searchResult "piece-type+instrumentation")))))))))

(defn test-ns-hook [] (check-that-search-returns-piece+instrumentation-types))

