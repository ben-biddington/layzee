(ns layzee.adapters.twitter.search
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :as logging]
            [bone.signature-base-string :as signature-base-string]
            [bone.signature :as signature]
            [layzee.adapters.twitter.authentication.bearer-tokens :as bearer-tokens]
            [layzee.net :refer :all]
            [layzee.paging :as paging]))

;; https://github.com/dakrone/clj-http
;; https://apps.twitter.com/app/8673064

(defn- bearer-auth[token] {
   "Authorization" (str "Bearer " token)
   "Accept"        "application/json"})

(defn- search-since[bearer-token what log opts])

(defn- search [bearer-token what log opts]
  "opts[:count] -- The number of results to return"
  (log opts)
  (let [page-size (or (:count opts) 15) tweet-filter (or (:filter opts) (fn[what] what)) max-id (or (:max-id opts) 0)]
    (let [url (format "https://api.twitter.com/1.1/search/tweets.json?count=%s&q=%s&max-id=%s" page-size (% what) (% (str max-id)))]
      (log url)
      (let [reply (http/get url {:headers (bearer-auth bearer-token)})]
        (log (:body reply))
        (filter tweet-filter (:statuses (json/read-str (:body reply) :key-fn keyword)))))))

(defn by-keyword[bearer-token keyword & opts]
  (logging/log opts)
  (search bearer-token keyword logging/log (if (nil? opts) {} (first opts))))


(defn- max-id[list]
  
  )

(defn by-keyword-paged[bearer-token keyword opts]
  "The idea here is to apply paging so that we can return all keyword search results"
  (let [limit (or (:limit opts) 15) page-size (or (:page-size opts) 100)]
    (paging/page
     #(search bearer-token keyword logging/log {:count limit :page-size page-size})
     #(<= limit (count %))
     '())))

(defn lazy-web
  ([bearer-token] (lazy-web bearer-token {}))
  ([bearer-token opts]
     (by-keyword bearer-token "#lazyweb" opts)))

