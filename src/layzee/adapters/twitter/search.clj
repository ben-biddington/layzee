(ns layzee.adapters.twitter.search
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :as logging]
            [bone.signature-base-string :as signature-base-string]
            [bone.signature :as signature]
            [layzee.adapters.twitter.authentication.bearer-tokens :as bearer-tokens]
            [layzee.net :refer :all]))

;; https://github.com/dakrone/clj-http
;; https://apps.twitter.com/app/8673064

(defn- bearer-auth[token] {
   "Authorization" (str "Bearer " token)
   "Accept"        "application/json"})

(defn- search[bearer-token what log opts]
  (log opts)
  (let [how-many (or (:count opts) 15) tweet-filter (or (:filter opts) (fn[what] what))]
    (let [url (format "https://api.twitter.com/1.1/search/tweets.json?count=%s&q=%s" how-many (% what))]
      (log url)
      (let [reply (http/get url {:headers (bearer-auth bearer-token)})]
        (log (:body reply))
        (filter tweet-filter (:statuses (json/read-str (:body reply) :key-fn keyword)))))))

(defn by-keyword[bearer-token keyword & opts]
  (search bearer-token keyword logging/log (if (nil? opts) {} (first opts))))

(defn lazy-web [bearer-token & opts]
  (by-keyword bearer-token "#lazyweb" opts))

