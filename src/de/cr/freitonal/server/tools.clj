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

(defn flatten [x]
  "if x is nil returns an empty list, otherwise returns a recursively flattened list of all elements"
  (if (nil? x)
    (list)
    (let [s? #(instance? clojure.lang.Sequential %)]
      (filter (complement s?) (tree-seq s? seq x)))))

(defn create-countmap [sequ]
  (loop [sequ* sequ
         countMap (hash-map)]
    (if (empty? sequ*)
      countMap
      (let [elem (first sequ*)]
        (recur (rest sequ*) (assoc countMap elem (+ 1 (get countMap elem 0))))))))