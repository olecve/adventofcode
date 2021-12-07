#!/usr/bin/env bb

(defn calculate-result [result]
      (* (:horizontal result) (:depth result)))

(let [lines (-> (or (first *command-line-args*) "input.txt") io/file slurp str/split-lines)]
  (->> lines
       (map #(str/split % #" "))
       (reduce (fn [result [action value]]
                 (println result)
                 (condp = action
                   "forward" (-> result
                                 (update :horizontal + (Integer/parseInt value))
                                 (update :depth #(+ % (* (:aim result) (Integer/parseInt value)))))
                   "down" (-> result
                              (update :aim + (Integer/parseInt value)))
                   "up" (-> result
                              (update :aim - (Integer/parseInt value)))
                   result))
               {:horizontal 0
                :depth 0
                :aim 0})
       calculate-result))
