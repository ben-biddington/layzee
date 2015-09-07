(ns layzee.core
  (:gen-class)
  (:refer-clojure :exclude[filter])
  (:require
   [layzee.adapters.twitter.search :refer :all :as twitter]
   [layzee.adapters.twitter.stream.filter :refer :all :as filter]
   [layzee.adapters.settings :refer :all :as settings]
   [layzee.use-cases.lazy-web :as lazy-web]
   [layzee.use-cases.realtime-sample :as realtime-sample]
   [clj-time.core :as t]
   [clj-time.format :as f]
   [clj-http.util :as util]))

(def ^{:private true} lazy-web-search
  (fn [opts]
    (twitter/lazy-web settings/oauth-credential opts)))

(defn- realtime-filter[args]
     (fn [callback]
       (filter/filter settings/oauth-credential callback args)))

;; VIEW

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

(defn- view[tweet]
  (format "[%s] %s %s %s" (local (:created_at tweet)) (earl tweet) (layout (-> tweet :user :name) 30) (-> tweet :text clean)))

(defn- view-all[how-many cached]
  (let [{timestamp :timestamp result :result} cached]
    (println (format "Searched for <%s> <#lazyweb> mentions and found <%s> results (filtered) -- [at: %s]" how-many (count result) (str timestamp)))
    (doseq [tweet result]
      (println (view tweet)))))

(defn -main [& args]
  (if (< 0 (count args))
    (realtime-sample/run { :realtime-fn (realtime-filter (rest args))})
    (lazy-web/run        { :search-adapter-fn lazy-web-search :ui-adapter-fn view-all})))
