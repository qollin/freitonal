(ns de.cr.freitonal.unittests.server.tools-test
  (:use [de.cr.freitonal.server.tools])
  (:use [de.cr.freitonal.unittests.server.testtools])
  (:use [clojure.test])
  (:import (de.cr.freitonal.shared.models Item)))

(deftest creating-a-simple-countmap []
  (let [countMap (create-countmap ["a", "a", "b"])]
    (is (= 2 (get countMap "a")))
    (is (= 1 (get countMap "b")))))

(deftest creating-a-countmap-with-items []
  (let [item1 (Item. "id1" "value1")
        item2 (Item. "id2" "value2")
        countMap (create-countmap [item1 item1 item2])]
    (is (= 2 (get countMap item1)))
    (is (= 1 (get countMap item2)))))

(deftest creating-a-countmap-with-equal-items []
  (let [item1 (Item. "id1" "value1")
        item1b (Item. "id1" "value1")
        item2 (Item. "id2" "value2")
        countMap (create-countmap [item1 item1b item2])]
    (is (= 2 (get countMap item1)))
    (is (= 2 (get countMap item1b)))
    (is (= 1 (get countMap item2)))))

(deftest test-remove-duplicates []
  (is (= '[1 2 3 4] (remove-duplicates '(1 1 2 2 3 3 4 4))))
  (is (= '[1 2 3 4] (remove-duplicates '(1 2 3 4 1 2 3 4))))
  (is (= '[1 2 3 4] (remove-duplicates '(1 2 3 4)))))

(deftest test-runonce []
  (let [f (run-once (fn [x] x))]
    (is (= 6 (f 6)))
    (is (= 6 (f 5)))))

(deftest test-str-null []
  (is (= "bla" (str-nil "bla")))
  (is (= "" (str-nil "")))
  (is (= nil (str-nil nil))))