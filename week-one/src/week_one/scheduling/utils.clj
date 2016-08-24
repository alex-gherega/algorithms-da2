(ns ^{:doc "Utilities for creating a scheduling of jobs"
      :author "avr.eng.phd. Alex Gherega"}
    scheduling.utils)

(def input "resources/jobs.txt")

(defn read-jobs [input]
  ;;(with-open [rdr (clojure.java.io/reader input)])
  (line-seq (clojure.java.io/reader input)))

(defn convert-line [l]
  (read-string (str "[" l "]")))

(defn extract-weight [booked-line]
  (-> booked-line second first))

(defn extract-len [booked-line]
  (-> booked-line second second))

(defn extract-score [booked-line]
  (-> booked-line first))

;; this can and should be optimized by returning a map from the getgo without traversing the sequence twice (i.e. without into)
(defn map-jobs [input f]
  (let [input (read-jobs input)]
    (into {} (map-indexed #(array-map %1
                                      (f %2))
                          (drop 1 input)))))

(defn srtby-weight [jb-map]
  (sort-by #(-> % val extract-weight) jb-map))

(defn srtby-score [jb-map]
  (sort-by #(-> % val extract-score) jb-map))

(defn completion [schedule job-key]
  (loop [current (first schedule)
         new (drop 1 schedule)
         c (-> current val extract-len)]
    (if (= job-key (key current))
      c
      ;; this way of handling maps is wrong; there's a cleaner way
      (recur (first new)
             (drop 1 new)
             (+ c (-> new first val extract-len))))))

(defn w-completion [schedule job-entry]
  (* (-> job-entry val extract-weight)
     (completion schedule (key job-entry))))

;; extract the inside anonymous function to a private fn
(defn weighted-completion [schedule]
  (first (reduce (fn [[sum current-ci] job-entry]
                   (let [ci (+ current-ci (-> job-entry val extract-len))
                         wi (-> job-entry val extract-weight)]
                     [(+ sum (* wi ci)) ci]))
                 [0 0]
                 schedule)))

;; instead on relying on sort-by should write you own sort or Comparator with better almost linear time
(defn schedule [input book-line]
  (-> input (map-jobs book-line) srtby-weight srtby-score reverse))
