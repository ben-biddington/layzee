(ns layzee.core
  (:gen-class)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]))

(defn- clean[text]
  (.replace text "\n" ""))

(defn- view[t]
  (format "[%s] %s -- %s" (:created_at t) (-> t :text clean) (-> t :user :name)))

(def no-retweets
     #(nil? (:retweeted_status %1)))

(defn -main [& args]
  (let [how-many 100]
    (let [result (twitter/lazy-web settings/consumer-token {:count how-many :filter no-retweets})]
      (println (format "Searched for <%s> <#lazyweb> mentions and found <%s> results (filtered)" how-many (count result)))

      (doseq [tweet result]
        (println (view tweet))))))
