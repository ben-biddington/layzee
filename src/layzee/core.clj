(ns layzee.core
  (:gen-class)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]
            [clj-http.util :as util]))

(defn- clean[text]
  (.replace text "\n" ""))

(defn- earl[t]
  ;; https://twitter.com/marick/status/633460947193978880
  (format "https://twitter.com/%s/status/%s" (util/url-encode (-> t :user :name)) (-> t :id)))

(defn- view[t]
  (format "[%s] %s -- %s %s" (earl t) (:created_at t) (-> t :text clean) (-> t :user :name)))

(def no-retweets
     #(nil? (:retweeted_status %1)))

(defn -main [& args]
  (let [how-many 100]
    (let [result (twitter/lazy-web settings/consumer-token {:count how-many :filter no-retweets})]
      (println (format "Searched for <%s> <#lazyweb> mentions and found <%s> results (filtered)" how-many (count result)))

      (doseq [tweet result]
        (println (view tweet))))))
