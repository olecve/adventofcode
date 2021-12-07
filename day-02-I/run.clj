#!/usr/bin/env bb

(let [lines (-> (or (first *command-line-args*) "input.txt") io/file slurp str/split-lines)]
  (->> lines
       (map #(str/split % #" "))
       (reduce (fn [result [action value]]
                 (condp = action
                   "forward" (update result :horizontal + (Integer/parseInt value))
                   "down" (update result :depth + (Integer/parseInt value))
                   "up" (update result :depth - (Integer/parseInt value))
                   result))
               {:horizontal 0
                :depth 0})
       vals
       (reduce *)))
