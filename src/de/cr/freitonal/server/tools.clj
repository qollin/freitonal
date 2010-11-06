(ns de.cr.freitonal.server.tools)

(defn d [text]
  (if (seq? text)
    (doseq [elem text]
      (d elem))
    (.. System err (println text)))
  text)

(defn append [v1 v2]
  (if (empty? v2) 
    v1
    (append (conj v1 (first v2)) (rest v2))))

(defn create-countmap [sequ]
  (loop [sequ* sequ
         countMap (hash-map)]
    (if (empty? sequ*)
      countMap
      (let [elem (first sequ*)]
        (recur (rest sequ*) (assoc countMap elem (+ 1 (get countMap elem 0))))))))

(defn remove-duplicates
  ([sequ] (remove-duplicates (rest sequ) (vector (first sequ)) (conj (hash-set) (first sequ))))
  ([sequ new-sequ acc]
    (if (empty? sequ)
      new-sequ
      (let [elem (first sequ)
            new-acc (conj acc elem)
            new-new-sequ (if (contains? acc elem)
                           new-sequ
                           (conj new-sequ elem))]
      (remove-duplicates (rest sequ) new-new-sequ new-acc)))))
      
