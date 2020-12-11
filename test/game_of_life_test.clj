(ns game-of-life-test
  (:require [game-of-life :as sut]
            [clojure.test :as t]))


(t/deftest next-state
  ;; 1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
  (t/is (= 0 (sut/next-state 1 0)))
  (t/is (= 0 (sut/next-state 1 1)))

  ;; 2. Any live cell with two or three live neighbours lives on to the next generation.
  (t/is (= 2 (sut/next-state 1 2)))
  (t/is (= 2 (sut/next-state 1 3)))
  (t/is (= 3 (sut/next-state 2 3)))

  ;; 3. Any live cell with more than three live neighbours dies, as if by overpopulation.
  (t/is (= 0 (sut/next-state 1 4)))

  ;; 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
  (t/is (= 1 (sut/next-state 0 3))))

(t/deftest next-individual-idx
  (let [generation [[0 0 0]
                    [1 1 1]
                    [0 0 0]]]
    (t/is (= (sut/next-individual-idx [0 0] generation) [1 0]))
    (t/is (= (sut/next-individual-idx [1 0] generation) [2 0]))
    (t/is (= (sut/next-individual-idx [2 0] generation) [0 1]))
    (t/is (= (sut/next-individual-idx [2 2] generation) [0 0]))))

(t/deftest next-genereation
  (t/is (= (sut/next-generation [[0 0 0]
                                 [1 1 1]
                                 [0 0 0]])
           [[0 1 0]
            [0 2 0]
            [0 1 0]]))

  (t/is (= (sut/next-generation [[0 0 1 0]
                                 [1 0 0 1]
                                 [1 0 0 1]
                                 [0 1 0 0]])
           [[0 0 0 0]
            [0 1 1 2]
            [2 1 1 0]
            [0 0 0 0]]))

  (t/is (= (sut/next-generation [[1 1 0 0]
                                 [1 1 0 0]
                                 [0 0 1 1]
                                 [0 0 1 1]])
           [[2 2 0 0]
            [2 0 0 0]
            [0 0 0 2]
            [0 0 2 2]]))

  (t/is (= (sut/next-generation [[2 2 0 0]
                                 [2 0 0 0]
                                 [0 0 0 2]
                                 [0 0 2 2]])
           [[3 3 0 0]
            [3 1 0 0]
            [0 0 1 3]
            [0 0 3 3]]))
  )
