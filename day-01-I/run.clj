#!/usr/bin/env bb

(let [numbers (-> (or (first *command-line-args*) "input.txt") io/file slurp str/split-lines)]
  (->> numbers
       (map #(Integer/parseInt %))
       (partition 2 1)
       (filter #(< (first %) (second %)))
       count))
