(ns layzee.use-cases.lazy-web
  (:gen-class)
  (:require [clojure.core.memoize :as memo]
            [clj-time.core :as t]
            [layzee.timing :as timing]))

(def ^{:private true} no-retweets
     #(nil? (:retweeted_status %1)))

(defn- assoc-replies-for[conversation-adapter-fn tweet]
  "Finds all the replies for a single tweet, returning a new tweet with its replies in the :replies field"
  (println "Finding replies for " (:id_str tweet))
  (assoc tweet (timing/time #(apply conversation-adapter-fn [(:id_str tweet)]) #(println (format "It took <%sms> to fund all of the replies" (:duration %))))))

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
