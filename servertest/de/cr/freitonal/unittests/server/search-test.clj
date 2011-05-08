(ns de.cr.freitonal.unittests.server.search-test
  (:use [de.cr.freitonal.server.search])
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.unittests.server.testtools])
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
                                          VolatilePiecePlusInstrumentationType))
  (:import (de.cr.freitonal.usertests.client.test.data TestData)))

(deftest test-add-search-clause []
  (is (= {:where "piece.composer_id IN (?, ?)"
          :from ["classical_piece piece"]
          :values ["1", "2"]}
        (add-search-clause ["piece-composer" ["1", "2"]])))
  (is (= {:where "piece.catalog_id IN (?)" 
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
                                    :where "b.b = ? AND b.c = ?"
                                    :values ["2" "3"]}))))
  (is (= {:from ["a" "b"]
          :where "a.a = ?"
          :values ["1" "2" "3"]}
        (combine-search-clauses '({:from ["a"]
                                   :where "a.a = ?"
                                   :values ["1"]}
                                   {:from  ["a" "b"]
                                    :values ["2" "3"]}))))
  (is (= {:from ["a" "b"]
          :where "a.a = ? AND NO values"
          :values ["1"]}
        (combine-search-clauses '({:from ["a"]
                                   :where "a.a = ?"
                                   :values ["1"]}
                                   {:from  ["a" "b"]
                                    :where "NO values"})))))

(deftest test-add-search-clauses []
  (is (= {:select "a.c1, b.c2"
           :from ["a" "b" "classical_piece piece"]
           :where (str (:where global-search-constraint) " AND a.b = b.id AND piece.composer_id IN (?)")
           :values ["1"]}
        (add-search-clauses 
          {:select "a.c1, b.c2"
           :from ["a" "b"]
           :where "a.b = b.id"}
           {"piece-composer" ["1"]})))
  (is (= {:select "a.c1, b.c2"
           :from ["a" "b" "classical_piece piece"]
           :where (str (:where global-search-constraint) " AND a.b = b.id AND piece.composer_id IN (?, ?)")
           :values ["1", "2"]}
        (add-search-clauses 
          {:select "a.c1, b.c2"
           :from ["a" "b"]
           :where "a.b = b.id"}
           {"piece-composer" ["1", "2"]}))))

(deftest search-for-violin-and-violin []
  (dbtest "test that searching for two violins does not find an instrumentation with one violin"
    (let [violin (insert-instrument "Violin")
          
          ;insert a piece that should not be found (it only includes _one_ violin):
          composerA (insert-composer (VolatileItem. "Alkan"))
          instrumentationA (insert-instrumentation "one violin" violin)
          pieceA (insert-piece (VolatilePiece. composerA instrumentationA))
          
          ;insert the piece that should be found (it includes two violins):
          composerB (insert-composer (VolatileItem. "Bartok"))
          instrumentationB (insert-instrumentation "two violins" violin violin)
          pieceB (insert-piece (VolatilePiece. composerB instrumentationB))
          
          ;do the search:
          searchResult (read-json (search {"piece-instrumentations__instrument" [(.getID violin), (.getID violin)]}) false)]
      (is (= 1 (count (searchResult "piece-composer"))))
      (is (= (.getID composerB) (str (first (first (searchResult "piece-composer")))))))))

(deftest check-that-search-returns-piece+instrumentation-types []
  (dbtest "test that a full search returns stuff from the piece plus instrumentation types table"
    (let [instrumentation (insert-instrumentation "one violin" violin)
          piecetype (insert-piecetype (VolatileItem. "Sonate"))
          piece (insert-piece (VolatilePiece. mozart instrumentation piecetype))
          piece+instrumentation-type (insert-piece+instrumentation-type (VolatilePiecePlusInstrumentationType. "Violinsonate" piecetype instrumentation))
          searchResult (read-json (search {}) false)]
      (is (= 1 (count (searchResult "piece-type+instrumentation"))))
      (is (= (.getID piece+instrumentation-type) (str (first (first (searchResult "piece-type+instrumentation")))))))))

(deftest check-that-instrumentations-are-returned-in-the-right-format []
  (dbtest ""
    (let [instrumentation (insert-instrumentation "violin piano combo" violin violin piano)
          piece (insert-piece (VolatilePiece. mozart instrumentation))
          jsonResult (search {})
          searchResult (read-json jsonResult false)]
      (is (some #(= % (Integer/valueOf (.getID instrumentation))) (map #(get % "id") (searchResult "piece-instrumentations"))))
      (is (some #(= % [(conj (item-vector violin) 2) (conj (item-vector piano) 1)])
            (map #(get % "instruments") (searchResult "piece-instrumentations")))))))

(deftest check-that-instrumentations-can-be-filtered-by-composer []
  (dbtest ""
    (let [violin+piano (insert-instrumentation "violin piano combo" violin piano)
          piece1 (insert-piece (VolatilePiece. beethoven violin+piano))
          piece2 (insert-piece (VolatilePiece. mozart piano-solo))
          fullSearchResult (read-json (search {}) false)
          composerSearchResult (read-json (search {"piece-composer" [(.getID beethoven)]}) false)]
      (is (= 2 (count (fullSearchResult "piece-instrumentations"))))
      (is (= 1 (count (composerSearchResult "piece-instrumentations")))))))

(deftest check-that-catalog-numbers-are-ordered-correctly []
  (dbtest ""
    (let [catalogname (insert-catalogname "Opus")
          catalog1 (insert-catalog catalogname "2", "a")
          catalog2 (insert-catalog catalogname "1", "a")
          piece1 (insert-piece (VolatilePiece. beethoven piano-solo catalog1))
          piece2 (insert-piece (VolatilePiece. beethoven piano-solo catalog2))
          fullSearchResult (read-json (search {}) false)]
      (is (= (second (first (fullSearchResult "piece-catalog__number"))) "1-a"))
      (is (= (second (second (fullSearchResult "piece-catalog__number"))) "2-a")))))

(deftest check-that-only-top-level-pieces-are-returned-by-search []
  (dbtest ""
    (let [parent-piece (insert-piece (VolatilePiece. beethoven piano-solo opus27-1))
          piece (insert-piece (VolatilePiece. mozart piano-solo opus27-1 parent-piece))
          fullSearchResult (read-json (search {"piece-catalog" [(.getID opus27-1)]}) false)]
      (is (not (containing? mozart (fullSearchResult "piece-composer")))))))

(deftest check-that-objects-not-referenced-by-any-piece-are-returned-from-initial-loading []
  (dbtest ""
    (let [baryton (insert-instrument "Baryton")
          baryton-solo (insert-instrumentation "baryton-solo" baryton)
          fullSearchResult (read-json (search {}) false)]
      (is (containing? baryton-solo (fullSearchResult "piece-instrumentations") #(% "id")))
      (is (containing? sonata (fullSearchResult "piece-piece_type") #(first %)))
      (is (containing? opus (fullSearchResult "piece-catalog__name") #(first %))))))

(deftest check-that-empty-fields-are-returned-once []
  (dbtest ""
    (let [check-for-empty-field (fn [field #^VolatilePiece piece]
                                  (let [full-piece (VolatilePiece. beethoven piano-solo sonata)]
                                    (.setCatalog full-piece opus27-1)
                                    (.setSubtitle full-piece TestData/Eroica)
                                    (.setMusicKey full-piece amajor)
                                    (.setPublicationDate full-piece TestData/Year1799)
                                    (.setOrdinal full-piece TestData/Ordinal4a)
                                    (insert-piece full-piece)
                                    (insert-piece piece)
                                    (let [fullSearchResult (read-json (search {}) false)]
                                      (is (= 2 (count (fullSearchResult field))))
                                      (is (some #{[nil nil]} (fullSearchResult field))))))]
    (check-for-empty-field "piece-piece_type" (VolatilePiece. mozart piano-solo))
    (sql/do-commands "DELETE FROM classical_piece")
    (check-for-empty-field "piece-catalog__name" (VolatilePiece. mozart piano-solo))
    (sql/do-commands "DELETE FROM classical_piece")
    (check-for-empty-field "piece-catalog__number" (VolatilePiece. mozart piano-solo))
    (sql/do-commands "DELETE FROM classical_piece")
    (check-for-empty-field "piece-subtitle" (VolatilePiece. mozart piano-solo))
    (sql/do-commands "DELETE FROM classical_piece")
    (check-for-empty-field "piece-music_key" (VolatilePiece. mozart piano-solo))
    (sql/do-commands "DELETE FROM classical_piece")
    (check-for-empty-field "piece-type_ordinal" (VolatilePiece. mozart piano-solo))
    (sql/do-commands "DELETE FROM classical_piece")
    (check-for-empty-field "piece-publication_date" (VolatilePiece. mozart piano-solo)))))

(deftest test-merge-instrumentRecords-into-instrumentations []
  (let [mergedRecords (merge-instrumentRecords-into-instrumentations [{:id 1 :nickname "bla" :instruments [[5 "piano"]]},
                                                                      {:id 1 :nickname "bla" :instruments [[7 "violin"]]}, 
                                                                      {:id 2 :nickname "blub" :instruments [[6 "viola"]]}])]
    (is (= 2 (count mergedRecords)))
    (is (= 1 (:id (first mergedRecords))))
    (is (= [[5 "piano"] [7 "violin"]] (:instruments (first mergedRecords))))))

(deftest test-list-pieces []
  (dbtest ""
    (let [piece (insert-piece (VolatilePiece. mozart piano-solo opus27-1))
          pieces (list-pieces {})]
      (is (= 1 (count pieces)))
      (is (= (.getID piece) (.getID (first pieces))))
      (is (= (.getComposerID (first pieces)) (.getID mozart)))
      (is (= (.getCatalogID (first pieces)) (.getID opus27-1)))
      (is (= (.getCatalogNameID (first pieces)) (.getID (.getCatalogName opus27-1))))
      (is (= (.getInstrumentationID (first pieces)) (.getID piano-solo)))
      (is (nil? (.getParentID (first pieces)))))))

(deftest test-list-pieces-with-search-parameter []
  (dbtest ""
    (let [piece (insert-piece (VolatilePiece. mozart piano-solo opus27-1))
          piece2 (insert-piece (VolatilePiece. beethoven piano-solo opus27-1))
          pieces (list-pieces {"piece-composer" [(.getID mozart)]})]
      (is (= 1 (count pieces))))))

(comment defn test-ns-hook []
  (check-that-empty-fields-are-returned-once))
