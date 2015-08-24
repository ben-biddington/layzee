(ns layzee.core
  (:gen-class)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]
            [clj-http.util :as util]
            [clj-time.core :as t]
            [clj-time.format :as f]))

(defn- clean[text]
  (.replace text "\n" ""))

(defn- layout[t width]
  (clojure.pprint/cl-format nil (format "~%sA" width) t))

(defn- earl[t]
  ;; https://twitter.com/marick/status/633460947193978880
  (layout (format "https://twitter.com/%s/status/%s" (util/url-encode (-> t :user :id_str)) (-> t :id)) 65))

(def local-date-format
     (f/formatter "dd-MM-yy HH:mm"))

(def twitter-date-format
     (f/formatter "EEE MMM dd HH:mm:ss Z yyyy"))

(defn- local[date] ;; https://github.com/clj-time/clj-time ;; Tue Aug 18 02:14:51 +0000 2015
  (f/unparse local-date-format (t/to-time-zone (f/parse twitter-date-format date) (t/time-zone-for-id "Pacific/Auckland"))))

(defn- view[t]
  (format "[%s] %s %s %s" (local (:created_at t)) (earl t) (layout (-> t :user :name) 30) (-> t :text clean)))

(def no-retweets
     #(nil? (:retweeted_status %1)))

(defn -main [& args]
  (let [how-many 100]
    (let [result (twitter/lazy-web settings/oauth-credential {:count how-many :filter no-retweets})]
      (println (format "Searched for <%s> <#lazyweb> mentions and found <%s> results (filtered)" how-many (count result)))

      (doseq [tweet result]
        (println (view tweet))))))
