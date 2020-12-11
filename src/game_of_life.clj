(ns game-of-life)

;;(set! *unchecked-math* :warn-on-boxed)

(defn individual-state [[x y] generation]
  (get-in generation [y x] 0))

(defn neighbour-idxs [[^int x-index ^int y-index]]
  [(vec (range (- x-index 1) (+ x-index 2)))
   (vec (range (- y-index 1) (+ y-index 2)))])

(defn neighbour-states
  [[current-x current-y] generation [x-indexs y-indexs]]
  (vec (for [x x-indexs
             y y-indexs
             :when (not (= [current-x current-y] [x y]))]
         (individual-state [x y] generation))))

(defn neighbours
  "Get neighbours state."
  [current-x-y generation]
  (->> (neighbour-idxs   current-x-y)
       (neighbour-states current-x-y generation)))

(defn next-state [current alives]
  (cond
    (and (pos-int? current) (< alives 2))     0
    (and (pos-int? current) (#{2 3} alives))  (inc current)
    (and (pos-int? current) (> alives 3))     0
    (and (#{0} current) (#{3} alives))        1
    :else                                     0))

(defn alives [v] (count (filter pos-int? v)))
(defn game-width  [generation] (count (first generation)))
(defn game-height [generation] (count generation))

(defn next-individual-idx [[x y] generation]
  (let [width (game-width generation)
        height (game-height generation)]
    [(mod (inc x) width)
     (mod (+ y (quot (inc x) width)) height)]))

(defn next-generation [generation]
  (let [end-idx [(dec (game-width generation)) (dec (game-height generation))]]
    (loop [new-gen generation
           [x y] [0 0]]
      (if (= end-idx [x y])
        (assoc-in new-gen [y x] (->> (neighbours [x y] generation)
                                     (alives)
                                     (next-state (individual-state [x y] generation))))
        (recur
         (assoc-in new-gen [y x] (->> (neighbours [x y] generation)
                                      (alives)
                                      (next-state (individual-state [x y] generation))))
         (next-individual-idx [x y] generation))))))
