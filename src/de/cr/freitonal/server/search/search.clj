(ns de.cr.freitonal.server.search.search
  (:use [clojure.contrib.sql :as sql :only ()])
  (:use [clojure.contrib.json.write :only (print-json)]))

(defn d [text]
  (.. System err (println text))
  text)

(defn append [v1 v2]
  (if (empty? v2) 
    v1
    (append (conj v1 (first v2)) (rest v2))))

(defn flatten [x]
  (let [s? #(instance? clojure.lang.Sequential %)]
    (filter (complement s?) (tree-seq s? seq x))))

(defn create-placeholder-string [values]
  (str "(" (apply str (interpose ", " (repeat (count values) "?"))) ")"))

(defn add-search-clause [[searchParam values]]
  (cond 
    (= searchParam "piece-composer")                     
    {:from ["classical_piece piece"]
     :where (str " AND piece.composer_id IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-instrumentations__instrument")
    {:from ["classical_piece piece", "classical_piece_instrumentations", "classical_instrumentation"
            "classical_instrumentation_instruments"]
     :where (str " AND classical_piece_instrumentations.piece_id = piece.id
       AND classical_piece_instrumentations.instrumentation_id = classical_instrumentation.id
       AND classical_instrumentation.id = classical_instrumentation_instruments.instrumentation_id
       AND classical_instrumentation_instruments.instrument_id IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-piece_type")
    {:from ["classical_piece piece"]
     :where (str " AND piece.piece_type_id IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-catalog__name")
    {:from ["classical_piece piece", "classical_catalog"]
     :where (str " AND piece.catalog_id = classical_catalog.id 
       AND classical_catalog.name_id IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-catalog__number")
    {:from ["classical_piece piece", "classical_catalog"]
     :where (str " AND piece.catalog_id = classical_catalog.id
       AND classical_catalog.ordinal IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-subtitle")
    {:from ["classical_piece piece"]
     :where (str " AND piece.subtitle IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-type_ordinal")
    {:from ["classical_piece piece"]
     :where (str " AND piece.type_ordinal IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-music_key")
    {:from ["classical_piece piece", "classical_musickey"]
     :where (str " AND piece.music_key_id = classical_musickey.id 
        AND classical_musickey.id IN " (create-placeholder-string values))
     :values values}
    :default (throw (new IllegalArgumentException (str searchParam " is not implemented yet as a search parameter")))))

(defn combine-search-clauses [searchClauses]
  {:from (vec (apply hash-set (flatten (map #(% :from) searchClauses))))
   :where (apply str (map #(% :where)  searchClauses))
   :values (vec (flatten (map #(% :values) (filter #(not (nil? (% :values))) searchClauses))))})

(defn add-search-clauses [sql searchParams]
  (assoc 
    (combine-search-clauses (conj (map add-search-clause searchParams) sql))
    :select (sql :select)))

(defn create-sql-string [sql]
  (str "SELECT " (sql :select)
    " FROM " (apply str (interpose ", " (sql :from)))
    " WHERE " (sql :where)))

(defn run-search-query [sql render-fn searchParams]
  (if (empty? searchParams)
    (sql/with-query-results res 
      (vector (create-sql-string sql))
      (doall (map render-fn res)))
    (let [extendedSQL (add-search-clauses sql searchParams)]
      (try 
        (sql/with-query-results res 
          (append [(create-sql-string extendedSQL)] (extendedSQL :values))
          (doall (map render-fn res)))
        (catch Exception e
          (d "something went wrong when executing")
          (d extendedSQL)
          (d "with")
          (d (extendedSQL :values))
          (d (.getMessage e)))))))

(defn render-composer [rec] 
  (vector
    (:id rec)
    (str (:last_name rec) 
      (if (not-empty (:first_name rec)) (str ", " (:first_name rec))) 
      (if (not-empty (:middle_name rec)) (str " " (:middle_name rec))))))

(defn search-composer [searchParams]
  (run-search-query
    {:select "DISTINCT composer.id, composer.first_name, composer.middle_name, composer.last_name" 
     :from ["classical_composer composer", "classical_piece piece"]
     :where "piece.composer_id = composer.id"}
    render-composer searchParams))

(defn render-name [rec] 
  (vector (:id rec) (:name rec)))

(defn search-label [searchParams]
  (run-search-query 
    {:select "DISTINCT label.id, label.name"
     :from ["classical_recordlabel label", "classical_recording recording", 
            "classical_piece piece", "classical_performance performance", "classical_recording_performances"]
     :where "recording.label_id = label.id
       AND classical_recording_performances.recording_id = recording.id
       AND classical_recording_performances.performance_id = performance.id
       AND performance.piece_id = piece.id"} 
    render-name searchParams))

(defn render-subtitle [rec]
  (vector (:subtitle rec) (:subtitle rec)))

(defn search-subtitle [searchParams]
  (run-search-query 
    {:select "DISTINCT piece.subtitle"
     :from ["classical_piece piece"]
     :where "piece.subtitle IS NOT NULL AND piece.subtitle <> ''"}
    render-subtitle searchParams))

(defn search-recording-type [searchParams]
  (run-search-query
    {:select "DISTINCT type.id, type.name"
     :from ["classical_recording recording", "classical_recordtype type", 
            "classical_piece piece", "classical_performance performance",
            "classical_recording_performances"]
     :where "recording.type_id = type.id
       AND classical_recording_performances.recording_id = recording.id
       AND classical_recording_performances.performance_id = performance.id
       AND performance.piece_id = piece.id"} 
    render-name searchParams))

(defn render-performance-location [rec] 
  (vector (:location rec) (:location rec)))

(defn search-performance-location [searchParams]
  (run-search-query
   {:select "DISTINCT performance.location"
    :from ["classical_performance performance", "classical_piece piece"]
    :where "performance.location IS NOT NULL AND performance.location <> ''
       AND performance.piece_id = piece.id"} 
   render-performance-location searchParams))

(defn render-performance-date [rec]
  (vector (:date rec) (:date rec)))

(defn search-performance-date [searchParams]
  (run-search-query
    {:select "DISTINCT performance.date"
     :from ["classical_performance performance", "classical_piece piece"]
     :where "performance.date IS NOT NULL AND performance.date <> ''
       AND performance.piece_id = piece.id"}
    render-performance-date searchParams))

(defn render-piece-type_ordinal [rec]
  (vector (:type_ordinal rec) (:type_ordinal rec)))

(defn search-piece-type_ordinal [searchParams]
  (run-search-query
    {:select "DISTINCT piece.type_ordinal"
     :from ["classical_piece piece"]
     :where "piece.type_ordinal IS NOT NULL AND piece.type_ordinal <> ''"}
    render-piece-type_ordinal searchParams))

(defn search-piece-piece_type [searchParams]
  (run-search-query
    {:select "DISTINCT type.id, type.name"
     :from ["classical_piecetype type", "classical_piece piece"]
     :where "piece.piece_type_id = type.id"}
    render-name searchParams))

(defn search-performance-performers [searchParams]
  (run-search-query
    {:select "DISTINCT performer.id, performer.first_name, performer.middle_name, performer.last_name"
     :from ["classical_performer performer", "classical_performance performance", 
            "classical_allocatedinstrument_performers", "classical_allocatedinstrumentation_allocated_instruments",
            "classical_piece piece"]
     :where "classical_allocatedinstrument_performers.performer_id = performer.id
       AND classical_allocatedinstrument_performers.allocatedinstrument_id = classical_allocatedinstrumentation_allocated_instruments.allocatedinstrument_id
       AND classical_allocatedinstrumentation_allocated_instruments.allocatedinstrumentation_id = performance.instrumentation_id
       AND performance.piece_id = piece.id"}
    render-composer searchParams))

(defn search-piece-instruments [searchParams]
  (run-search-query 
    {:select "DISTINCT instrument.id, instrument.name"
     :from ["classical_instrument instrument", "classical_piece piece", 
            "classical_piece_instrumentations", "classical_instrumentation_instruments instruments",
            "classical_instrumentation"]
     :where "instruments.instrument_id = instrument.id
       AND instruments.instrumentation_id = classical_instrumentation.id
       AND classical_piece_instrumentations.piece_id = piece.id
       AND classical_instrumentation.id = classical_piece_instrumentations.instrumentation_id"}
    render-name searchParams))

(defn search-piece-music-key [searchParams]
  (run-search-query
    {:select "DISTINCT classical_musickey.id, classical_musickey.generated_title AS name"
     :from ["classical_musickey", "classical_piece piece"]
     :where "piece.music_key_id = classical_musickey.id"}
    render-name searchParams))

(defn search-piece-catalog-name [searchParams]
  (run-search-query
    {:select "DISTINCT type.id, type.name"
     :from ["classical_catalog", "classical_piece piece", "classical_catalogtype type"]
     :where "piece.catalog_id = classical_catalog.id
       AND classical_catalog.name_id = type.id"}
    render-name searchParams))

(defn render-catalog-number [rec]
  (vector (:id rec) (str (:ordinal rec) (if (re-matches #"[^ ]" (:sub_ordinal rec)) (str "-" (:sub_ordinal rec))))))

(defn search-piece-catalog-number [searchParams]
  (run-search-query
    {:select "DISTINCT classical_catalog.id, classical_catalog.ordinal, classical_catalog.sub_ordinal"
     :from ["classical_catalog", "classical_piece piece"] 
     :where "piece.catalog_id = classical_catalog.id
       AND classical_catalog.ordinal IS NOT NULL
       AND classical_catalog.ordinal <> ''"}
    render-catalog-number searchParams))

(defn search [searchParams]
  (with-out-str
    (print-json (hash-map "piece-composer" (search-composer searchParams)
                  "recording-label" (search-label searchParams)
                  "piece-subtitle" (search-subtitle searchParams)
                  "recording-type" (search-recording-type searchParams)
                  "performance-location" (search-performance-location searchParams)
                  "performance-date" (search-performance-date searchParams)
                  "piece-type_ordinal" (search-piece-type_ordinal searchParams)
                  "piece-piece_type" (search-piece-piece_type searchParams)
                  "performance-instrumentation__allocated_instruments__performers" (search-performance-performers searchParams)
                  "piece-instrumentations__instruments" (search-piece-instruments searchParams)
                  "piece-music_key" (search-piece-music-key searchParams)
                  "piece-catalog__name" (search-piece-catalog-name searchParams)
                  "piece-catalog__number" (search-piece-catalog-number searchParams)))))

(defn java-to-clojure [dataStructure]
  (let [clojureMap (into {} dataStructure)]
    (reduce #(assoc %1 %2 (into [] (%1 %2))) clojureMap (keys clojureMap))))

(defn doSearch [conf-file searchParams]
  (sql/with-connection (load-file conf-file)
    (search (java-to-clojure searchParams))))