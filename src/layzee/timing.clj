(ns layzee.timing
  (:refer-clojure :exclude [time])
  (:require [clojure.core.memoize :as memo]
            [clj-time.core :as t]))

(defn time [fn]
  (let [start (. System (nanoTime))]
    (let [result (apply fn [])]
      (let [end (. System (nanoTime))])
      {
       :result result
       :duration (/ (double (- (. System (nanoTime)) start)) 1000000.0)})))

