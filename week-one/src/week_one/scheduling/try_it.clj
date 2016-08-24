(ns ^{:doc "Test the code out"
      :author "avr.eng.phd. Alex Gherega"}
    scheduling.try-it
  (:require [scheduling.greedy-difference :as grdiff]
            [scheduling.greedy-ratio :as grratio]
            [scheduling.utils :as utils]))

(println "With difference: " (->> (grdiff/schedule "resources/jobs.txt")
                                  utils/weighted-completion)
         "\nWith ratio:" (->> (grratio/schedule "resources/jobs.txt")
                              utils/weighted-completion)
)
