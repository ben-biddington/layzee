(ns layzee.use-cases.lazy-web
  (:gen-class)
  (:require [clojure.test :refer :all]
            [clojure.core.memoize :as memo]
            [clj-time.core :as t]))

(def ^{:private true} no-retweets
     #(nil? (:retweeted_status %1)))

(defn- result[adapters how-many]
  {
   :timestamp (t/now)
   :result (apply (:search-adapter-fn adapters) [{:count how-many :filter no-retweets}])})

(def ^{:private true} cached-result
  (let [ttl-in-seconds (* 60 5)]
    (memo/ttl result :ttl/threshold (* 1000 ttl-in-seconds)))) ;; http://clojure.github.io/core.memoize/

(defn run
  ([adapters] (run adapters {:count 100}))
  ([adapters opts]
     (apply cached-result [adapters (:count opts)])))
