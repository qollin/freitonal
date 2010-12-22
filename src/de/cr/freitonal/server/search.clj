(ns de.cr.freitonal.server.search
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.server.javainterop])  
  (:use [de.cr.freitonal.server.render])
  
  (:use [clojure.contrib.sql :as sql :only ()])
  (:use [clojure.contrib.json :only (json-str)]))

(defn create-placeholder-string [values]
  (str "(" (apply str (interpose ", " (repeat (count values) "?"))) ")"))

(defn create-subselect-for-instrumentations [values]
  "values: a map with instrument ids as keys and instrument count as values"
  (let [subselect "SELECT DISTINCT instrumentation_id
                   FROM classical_instrumentationmember
                   WHERE instrument_id = ?
                   AND numberOfInstruments = ?"]
    (if (= (count values) 1)
      subselect
      (str "SELECT * FROM ("
           (apply str (interpose " UNION ALL " (repeat (count values) subselect)))
           ") AS tbl 
              GROUP BY tbl.instrumentation_id
              HAVING count(*) = " (count values)))))

(defn add-search-clause [[searchParam values]]
  (cond 
    (= searchParam "piece-composer")                     
    {:from ["classical_piece piece"]
     :where (str " AND piece.composer_id IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-instrumentations__instrument")
    {:from ["classical_piece piece", "classical_piece_instrumentations", "classical_instrumentation"
            "classical_instrumentationmember"]
     :where (str " AND classical_piece_instrumentations.piece_id = piece.id
       AND classical_piece_instrumentations.instrumentation_id = classical_instrumentation.id
       AND classical_instrumentation.id IN (" (create-subselect-for-instrumentations values) ")")
     :values (flatten (seq values))}
    (= searchParam "piece-piece_type")
    {:from ["classical_piece piece"]
     :where (str " AND piece.piece_type_id IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-catalog__name")
    {:from ["classical_piece piece", "classical_catalog"]
     :where (str " AND piece.catalog_id = classical_catalog.id 
       AND classical_catalog.name_id IN " (create-placeholder-string values))
     :values values}
    (= searchParam "piece-catalog")
    {:from ["classical_piece piece"]
     :where (str " AND piece.catalog_id IN " (create-placeholder-string values))
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
  {:from (vec (set (flatten (map #(% :from) searchClauses))))
   :where (apply str (map #(% :where)  searchClauses))
   :values (vec (flatten (map #(% :values) (filter #(not (nil? (% :values))) searchClauses))))})

(defn add-search-clauses [sql searchParams]
  (assoc 
    (combine-search-clauses (conj (map add-search-clause searchParams) sql))
    :select (sql :select)))

(defn create-sql-string [sql]
  (str "SELECT " (sql :select)
    " FROM " (apply str (interpose ", " (sql :from)))
    " WHERE " (sql :where)
    (if (sql :order)
      (str " ORDER BY " (sql :order)))))

(defn create-query [sql searchParams]
  (if (empty? searchParams)
    (vector (create-sql-string sql))
    (let [extendedSQL (add-search-clauses sql searchParams)]
      (append [(create-sql-string extendedSQL)] (extendedSQL :values)))))

(defn run-search-query [sql render-fn searchParams]
  (let [query (create-query sql searchParams)]
    (try
      (comment (d query))
      (sql/with-query-results res
        query
        (doall (map render-fn res)))
      (catch Exception e
        (d "something went wrong when executing")
        (d query)
        (d (.printStackTrace e))))))

(defn process-search-params [searchParams]
  (if (contains? searchParams "piece-instrumentations__instrument")
    (assoc searchParams "piece-instrumentations__instrument" 
      (create-countmap (searchParams "piece-instrumentations__instrument")))
    searchParams))

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
            "classical_piece_instrumentations", "classical_instrumentationmember instruments",
            "classical_instrumentation"]
     :where "instruments.instrument_id = instrument.id
       AND instruments.instrumentation_id = classical_instrumentation.id
       AND classical_piece_instrumentations.piece_id = piece.id
       AND classical_instrumentation.id = classical_piece_instrumentations.instrumentation_id"}
    render-name searchParams))

(defn render-instrumentation [rec]
  (hash-map :id (:id rec) 
            :nickname (:nickname rec)
            :instruments (vector (vector (:instrument_id rec) (:name rec)))))

(defn merge-instrumentRecords-into-instrumentations 
  ([instrumentRecords] 
    (merge-instrumentRecords-into-instrumentations (rest instrumentRecords) (hash-map (:id (first instrumentRecords)) (first instrumentRecords))))
  ([instrumentRecords acc]
    (if (empty? instrumentRecords)
      (vals acc)
      (let [rec (first instrumentRecords)
            id (:id rec)
            new-acc (if (contains? acc id)
                      (assoc acc id (assoc rec :instruments (append (get-in acc [id :instruments]) (:instruments rec))))
                      (assoc acc id rec))]
        (merge-instrumentRecords-into-instrumentations (rest instrumentRecords) new-acc)))))

(defn search-piece-instrumentations [searchParams]
  (let [instrumentRecords (run-search-query
                            {:select "DISTINCT classical_instrumentation.id, 
                                               classical_instrumentation.nickname, 
                                               instruments.instrument_id,
                                               instrument.name"
                             :from ["classical_piece piece", "classical_instrumentation", "classical_piece_instrumentations",
                                    "classical_instrumentationmember instruments", "classical_instrument instrument"]
                             :where "classical_piece_instrumentations.piece_id = piece.id
                               AND classical_piece_instrumentations.instrumentation_id = classical_instrumentation.id
                               AND instruments.instrumentation_id = classical_instrumentation.id
                               AND instruments.instrument_id = instrument.id"
                             :order "instruments.ordinal"}
                            render-instrumentation searchParams)]
    (merge-instrumentRecords-into-instrumentations instrumentRecords)))

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
  (vector 
    (:id rec) 
    (create-string-from-ordinal-and-subordinal (:ordinal rec) (:sub_ordinal rec)))) 

(defn search-piece-catalog-number [searchParams]
  (run-search-query
    {:select "DISTINCT classical_catalog.id, classical_catalog.ordinal, classical_catalog.sub_ordinal"
     :from ["classical_catalog", "classical_piece piece"] 
     :where "piece.catalog_id = classical_catalog.id
       AND classical_catalog.ordinal IS NOT NULL
       AND classical_catalog.ordinal <> ''"
     :order "classical_catalog.ordinal, classical_catalog.sub_ordinal"}
    render-catalog-number searchParams))

(defn search-piece-type+instrumentation [searchParams]
  (run-search-query
    {:select "DISTINCT classical_typeplusinstrumentation.id, classical_typeplusinstrumentation.nickname AS name"
     :from ["classical_typeplusinstrumentation", "classical_piece piece", "classical_piece_instrumentations"]
     :where "classical_typeplusinstrumentation.type_id = piece.piece_type_id
       AND classical_typeplusinstrumentation.instrumentation_id = classical_piece_instrumentations.instrumentation_id"}
    render-name searchParams))

(defn render-publication-date [rec]
  (vector (:pub_date rec) (:pub_date rec)))

(defn search-piece-publication-date [searchParams]
  (run-search-query 
    {:select "DISTINCT piece.pub_date"
     :from ["classical_piece piece"]
     :where "piece.pub_date IS NOT NULL AND piece.pub_date <> ''"}
    render-publication-date searchParams))

(defn search [searchParams]
  (let [processedSearchParams (process-search-params searchParams)]
    (json-str (hash-map 
                "piece-composer" (search-composer processedSearchParams)
                "piece-subtitle" (search-subtitle processedSearchParams)
                "piece-type_ordinal" (search-piece-type_ordinal processedSearchParams)
                "piece-piece_type" (search-piece-piece_type processedSearchParams)
                "piece-instrumentations__instruments" (search-piece-instruments processedSearchParams)
                "piece-instrumentations" (search-piece-instrumentations processedSearchParams)
                "piece-music_key" (search-piece-music-key processedSearchParams)
                "piece-catalog__name" (search-piece-catalog-name processedSearchParams)
                "piece-catalog__number" (search-piece-catalog-number processedSearchParams)
                "piece-type+instrumentation" (search-piece-type+instrumentation processedSearchParams)
                "piece-publication_date" (search-piece-publication-date processedSearchParams)
                
                "performance-location" (search-performance-location processedSearchParams)
                "performance-date" (search-performance-date processedSearchParams)
                "performance-instrumentation__allocated_instruments__performers" (search-performance-performers processedSearchParams)
                
                "recording-label" (search-label processedSearchParams)
                "recording-type" (search-recording-type processedSearchParams)))))

(defn doSearch [conf-file searchParams]
  (sql/with-connection (load-file conf-file)
    (search (java-to-clojure searchParams))))