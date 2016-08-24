(ns ^{:doc "Run a csheduling algorithms aligning jobs in decreasing order of
            diffrerence (weight - length)"
     :author "avr.eng.phd. Alex Gherega"}
  scheduling.greedy-difference
  (:require [scheduling.utils :as utils]))

;; In this programming problem and the next you'll code up the greedy algorithms from lecture for minimizing the weighted sum of completion times..

;; Download the text file below.

;; jobs.txt
;; This file describes a set of jobs with positive and integral weights and lengths. It has the format

;; [number_of_jobs]

;; [job_1_weight] [job_1_length]

;; [job_2_weight] [job_2_length]

;; ...

;; For example, the third line of the file is "74 59", indicating that the second job has weight 74 and length 59.

;; You should NOT assume that edge weights or lengths are distinct.

;; Your task in this problem is to run the greedy algorithm that schedules jobs in decreasing order of the difference (weight - length). Recall from lecture that this algorithm is not always optimal. IMPORTANT: if two jobs have equal difference (weight - length), you should schedule the job with higher weight first. Beware: if you break ties in a different way, you are likely to get the wrong answer. You should report the sum of weighted completion times of the resulting schedule --- a positive integer --- in the box below.

;; ADVICE: If you get the wrong answer, try out some small test cases to debug your algorithm (and post your test cases to the discussion forum).

;; FLOW:

;; a) read file input (jobs.txt)
;; b) for each input lein (weight/length) compute the difference weight-length
;; c) have a booking system from job to weight-lenght difference
;; d) create the scheduling solution: sort in decreasing order
;; e) compute completion time for a job in the schedule obating at d)
;; f) compute the weighted completion time a job
;; g) compute the sum of weighted completion times

(defn book-line [l]
  (let [v (utils/convert-line l)]
    [(apply - v) v]))

;; instead on relying on sort-by should write you own sort or Comparator with better almost linear time
(defn schedule [input]
  (utils/schedule input book-line))
