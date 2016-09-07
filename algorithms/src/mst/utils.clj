(ns ^{:doc "Utilities for creating the Minimum Spanning Tree of a unidirected graph"
      :author "avr.eng.phd. Alex Gherega"}
    mst.utils
  (:require [utils :as wols]))

(def input "resources/edges.txt")

;; logical operations
(defn find-first [pred coll]
  (some #(when (pred %) %) coll))

(defn eqpred [vertex edge]
  (= vertex (first edge)))

(defn first-edge [vertex graph] ;; graph is a sequence of vectors each vector of format [vertex-i vertex-j cost] with i <= j
  (find-first (partial eqpred vertex)
                    graph))

(defn has-vertex? [edge vertex]
  (some #{vertex} edge))

;; input operations
(defn edge [booked-edge]
  ((juxt first second) booked-edge))

(defn sort-edge [edge]
  (if (apply > edge)
    (reverse edge)
    edge))

;; if input edges are not sorted small-index-vertex first
(defn- sort-line [booked-edge]
  (conj (-> booked-edge edge sort-edge)
        (wols/third booked-edge)))

(defn cost [booked-edge]
  (wols/third booked-edge))

;; graph operations

;; TODO: try without sort-by edge
(defn map-graph [input]
  (let [input (wols/read-lines input)]
    (sort-by cost
             (map #(-> % wols/convert-line) ;; include sort-line if edges
                  ;; are not sorted
                  (drop 1 input)))))

(defn vertices [graph] ;; graph is a sequence of vectors each vector of format [vertex-i vertex-j cost] with i <= j
  (let [redux (reduce #(conj %1 (first %2) (second %2)) #{} graph)
        last-edge (-> graph last edge)]
    ;; use the commented code if you need to include all nodes (usually if no wierd cycles you'll get (count nodex) - 1)
    ;; (if (apply = last-edge)
    ;;   redux
    ;;   (conj redux (second last-edge)))
    redux))

(defn rm [graph vertex f] ;; graph is a sequence of vectors each vector of format [vertex-i vertex-j cost] with i <= j
  ;(filter #(some (partial f vertex) %) graph))
  (filter #(-> % edge (has-vertex? vertex) f) graph))

(defn one-cut [graph vertex] ;; graph is a sequence of vectors each vector of format [vertex-i vertex-j cost] with i >= j
  ((juxt #(rm % vertex identity)
         #(rm % vertex not)) graph))

(defn cut [graph vertices]
  (loop [vertices vertices
         lcut-graph (lazy-seq '())
         rcut-graph graph]
    (if (empty? vertices)
      [lcut-graph rcut-graph]
      (recur (rest vertices)
             (into lcut-graph (first (one-cut rcut-graph (first vertices))))
             (second (one-cut rcut-graph (first vertices)))))))

(defn cutting-edges [graph xes ves]
  (let [filter-fn #(-> %1 edge (has-vertex? %2))
        xes (if (empty? xes) ves xes)]
    (filter #(and (some (partial filter-fn %) xes)
                  (some (partial filter-fn %) ves)) graph)))
