#!/usr/bin/env bb

(let [lines (-> (or (first *command-line-args*) "input.txt") io/file slurp str/split-lines)]
  (->> lines
       (map #(Integer/parseInt %))
       (partition 3 1)
       (map #(apply + %))
       (partition 2 1)
       (filter #(< (first %) (second %)))
       count))
