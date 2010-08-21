(ns de.cr.freitonal.server.testtools
  (:use [clojure.test])
  (:use [clojure.contrib.sql :as sql :only ()]))

(defmacro dbtest [description & body]
  `(sql/with-connection (load-file "conf/db-empty.clj")
    (sql/transaction
      (sql/set-rollback-only)
      ~@body)))

(defn runTests [package]
  "given a clojure package as a String it runs all tests in this package"
  (sql/with-connection (load-file "conf/db.clj") 
    (let [summary (run-tests (symbol package))]
      (zipmap (map #(str %) (keys summary)) (vals summary)))))
