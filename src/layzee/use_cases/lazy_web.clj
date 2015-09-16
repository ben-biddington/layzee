(ns layzee.use-cases.lazy-web
  (:gen-class)
  (:require [clojure.core.memoize :as memo]
            [clj-time.core :as t]
            [layzee.timing :as timing]))

(def ^{:private true} no-retweets
     #(nil? (:retweeted_status %1)))

(defn- result[adapters how-many]
  (let [result (timing/time #(apply (:search-adapter-fn adapters) [{:count how-many :filter no-retweets}]) #(println (format "It took <%sms> to search" (:duration %))))]
    (let [results-with-replies (pmap (partial (:conversation-adapter-fn adapters)) result)]
    {
     :timestamp (t/now)
     :result results-with-replies})))

(def ^{:private true} cached-result
  (let [ttl-in-seconds (* 60 5)]
    (memo/ttl result :ttl/threshold (* 1000 ttl-in-seconds)))) ;; http://clojure.github.io/core.memoize/

(defn run
  ([adapters] (run adapters {:count 100}))
  ([adapters opts]
     (apply cached-result [adapters (:count opts)])))
