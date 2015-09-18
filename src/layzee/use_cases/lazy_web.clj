(ns layzee.use-cases.lazy-web
  (:gen-class)
  (:require [clojure.core.memoize :as memo]
            [clj-time.core :as t]
            [layzee.timing :as timing]))

(def ^{:private true} no-retweets
     #(nil? (:retweeted_status %1)))

(defn- result[search-fn conversation-fn log-fn how-many]
  (let [result (timing/time #(apply search-fn [{:count how-many :filter no-retweets}])       #(log-fn "It took <%sms> to search. Found <%s> tweets."  (:duration %) (count result)))]
    (let [results-with-replies (timing/time #(doall (pmap (partial conversation-fn) result)) #(log-fn "It took <%sms> to find replies to <%s> tweets" (:duration %) (count result)))]
      {
       :timestamp (t/now)
       :result results-with-replies})))

(def ^{:private true} cached-result
  (let [ttl-in-seconds (* 60 5)]
    (memo/ttl result :ttl/threshold (* 1000 ttl-in-seconds)))) ;; http://clojure.github.io/core.memoize/

(defn run
  ([adapters] (run adapters {:count 100}))
  ([adapters opts]
     (apply cached-result [(:search-adapter-fn adapters) (:conversation-adapter-fn adapters) (:log-fn adapters) (:count opts)])))
