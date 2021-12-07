#!/usr/bin/env bb

(defn binary->int [binary]
  (Integer/parseInt binary 2))

(defn matrix->gamma-rate [matrix]
  (->> matrix
       (map #(->> %
                  frequencies
                  (sort-by val)
                  reverse
                  first
                  first))
       str/join
       binary->int))

(defn matrix->epsilon-rate [matrix]
  (->> matrix
       (map #(->> %
                  frequencies
                  (sort-by val)
                  first
                  first))
       str/join
       binary->int))

(let [lines (-> (or (first *command-line-args*) "./day-03-I/input-test.txt") io/file slurp str/split-lines)
      matrix (->> lines
                  (map #(str/split % #""))
                  (apply map list))
      gamma-rate (matrix->gamma-rate matrix)
      epsilon-rate (matrix->epsilon-rate matrix)]
  (* gamma-rate epsilon-rate))
