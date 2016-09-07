(ns ^{:doc "Utilities for reading inputs, transformations, parsing etc."
      :author "avr.eng.phd. Alex Gherega"}
    utils)

(defn read-lines [input]
  ;;(with-open [rdr (clojure.java.io/reader input)])
  (line-seq (clojure.java.io/reader input)))

(defn convert-line [l]
  (read-string (str "[" l "]")))

(defn third [seq]
  (nth seq 2))
