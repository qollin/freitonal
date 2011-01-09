(ns de.cr.freitonal.server.javainterop
  (:import (java.util ArrayList)))

(defn create-ArrayList [& items]
  (let [arrayList (ArrayList.)]
    (doseq [item items]
      (.add arrayList item))
    arrayList))

(defn java-to-clojure [dataStructure]
  (let [clojureMap (into {} dataStructure)]
    (reduce #(assoc %1 %2 (into [] ((str %1) (str %2)))) clojureMap (keys clojureMap))))
