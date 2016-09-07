(ns ^{:doc "Test the code out"
      :author "avr.eng.phd. Alex Gherega"}
    mst.try-it
  (:require [mst.utils :as utils]
            [mst.prim :as prim]))

(println "Cost of the MST: " (-> "resources/edges.txt"
                                 utils/map-graph
                                 prim/prim
                                 prim/prim-cost
                                 time))
