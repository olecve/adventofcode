#!/usr/bin/env bb

(defn binary->int [binary]
  (Integer/parseInt binary 2))

(defn transpose [matrix]
  (apply map list matrix))

(defn find-most-frequent [matrix index]
  (let [tuples (-> matrix
                  transpose
                  (nth index)
                  frequencies)]
    (cond
      (= 1 (count tuples))
      (first (first tuples))

      (apply = (vals tuples))
      "1"

      :else
      (first (first (reverse (sort-by val tuples)))))))

(defn find-lest-frequent [matrix index]
  (let [tuples (-> matrix
                   transpose
                   (nth index)
                   frequencies)]
    (cond
      (= 1 (count tuples))
      (first (first tuples))

      (apply = (vals tuples))
      "0"

      :else
      (first (first (sort-by val tuples))))))

(defn find-value [matrix length find-fn]
  (loop [result []
         index 0
         matrix matrix]
    (if (= index length)
      (-> result str/join binary->int)
      (let [value (find-fn matrix index)
            updated-matrix (filter #(= (nth % index) value) matrix)]
        (recur (conj result value)
               (inc index)
               updated-matrix)))))

(let [lines (-> (or (first *command-line-args*) "./day-03-II/input-test.txt") io/file slurp str/split-lines)
      matrix (map #(str/split % #"") lines)
      length (count (first lines))
      oxygen-generator-rating (find-value matrix length find-most-frequent)
      co2-scrubber-rating (find-value matrix length find-lest-frequent)]
  (* oxygen-generator-rating co2-scrubber-rating))
