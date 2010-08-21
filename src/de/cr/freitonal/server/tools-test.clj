(ns de.cr.freitonal.server.tools-test
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.server.testtools])
  
  (:use [clojure.test]))

(deftest test-create-countmap []
  (let [countMap (create-countmap ["a", "a", "b"])]
    (is (= 2 (get countMap "a")))
    (is (= 1 (get countMap "b")))))
