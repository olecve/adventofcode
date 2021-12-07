#!/usr/bin/env bb

(defn line-coordinates->line-points [{:keys [x1 y1 x2 y2]}]
  (cond
    (= y1 y2)
    (for [x (range (min x1 x2) (inc (max x1 x2)))]
      [x y1])

    (= x1 x2)
    (for [y (range (min y1 y2) (inc (max y1 y2)))]
      [x1 y])

    (and (< y1 y2) (< x1 x2))
    (map-indexed (fn [index x]
                   [x (+ y1 index)])
                 (range x1 (inc x2)))

    (and (< y1 y2) (> x1 x2))
    (map-indexed (fn [index x]
                   [x (+ y1 index)])
                 (reverse (range x2 (inc x1))))

    (and (> y1 y2) (< x1 x2))
    (map-indexed (fn [index x]
                   [x (- y1 index)])
                 (range x1 (inc x2)))

    (and (> y1 y2) (> x1 x2))
    (map-indexed (fn [index x]
                   [x (- y1 index)])
                 (reverse (range x2 (inc x1))))))

(let [lines (-> (or (first *command-line-args*) "input.txt") io/file slurp str/split-lines)]
  (->> lines
       (map #(str/split % #" "))
       (map (fn [[point-1 _ point-2]]
              (let [[x1 y1] (map #(Integer/parseInt %) (str/split point-1 #","))
                    [x2 y2] (map #(Integer/parseInt %) (str/split point-2 #","))]
                {:x1 x1
                 :y1 y1
                 :x2 x2
                 :y2 y2})))
       (mapcat line-coordinates->line-points)
       frequencies
       vals
       (filter #(< 1 %))
       count))
