(ns ^{:doc "Prim's Minimum Spanning Tree of a unidirected graph"
      :author "avr.eng.phd. Alex Gherega"}
    .mst.prim
  (:require [utils :as wols]
            [mst.utils :as utils]))

(defn prim-conj [graph xes ves] ;; graph is a sequence of vectors each vector of format [vertex-i vertex-j cost] with i <= j
  ;; get all edges of vertex
  ;; ves U xes = {graph's vertices}
  ;; ves and xes are disjoint
  (let [edges (utils/cutting-edges graph xes ves)]
    ;; determine the lowest cost edge in th above collection
    (first (sort-by wols/third edges))))

(defn booking [graph tree vertices]
  (let [xes (utils/vertices tree)
        cheapest (prim-conj graph xes vertices)
        tree (conj tree cheapest)]
    [(apply disj vertices (utils/edge cheapest))
     tree
     ]))

(defn prim-check [graph tree] ;; graph/tree is a sequence of vectors each vector of format [vertex-i vertex-j cost] with i <= j
  (= (utils/vertices graph) (utils/vertices tree)))

(defn prim [graph] ;; graph is a sequence of vectors each vector of format [vertex-i vertex-j cost] with i <= j
  (loop [[ves tree] [(utils/vertices graph)
                     #{}]]

    (if (and (empty? ves) (prim-check graph tree))
      tree
      (recur (booking graph tree ves)))))


(defn prim-cost [tree] ;; tree is a sequence of unique vectors each vector of format [vertex-i vertex-j cost] with i >= j
  (reduce #(+ %1 (wols/third %2))
          0
          tree))
