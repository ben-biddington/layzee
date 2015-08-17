(ns layzee.adapters.twitter
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]))

;; https://dev.twitter.com/oauth/application-only
;; https://github.com/dakrone/clj-http
;; https://apps.twitter.com/app/8673064

(defn- %[what] (util/url-encode what))
(defn- %64[what] (util/base64-encode (util/utf8-bytes what)))

(defn- sign[consumer-token]
  (let [{key :key secret :secret} consumer-token]
    (%64 (str (% key) ":" (% secret))))) ;; -> https://dev.twitter.com/oauth/application-only

(defn- headers[consumer-token]
  {
   "Authorization" (str "Basic " (sign consumer-token))
   "Content-type" "application/x-www-form-urlencoded;charset=UTF-8"})

(defn- bearer-auth[token]
  {
   "Authorization" (str "Bearer " token)
   "Accept" "application/json"})

(defn- bearer-token-for[consumer-token]
  (println (headers consumer-token))
  (let [reply (http/post
               "https://api.twitter.com/oauth2/token"
               {
                :headers (headers consumer-token)
                :body "grant_type=client_credentials"})]
    (:access_token (json/read-str (:body reply) :key-fn keyword))))

(defn- search[bearer-token what & opts]
  (let [reply (http/get
               "https://api.twitter.com//1.1/statuses/user_timeline.json?count=100&screen_name=twitterapi"
               {:headers (bearer-auth bearer-token)})]
    (json/read-str (:body reply) :key-fn keyword)))

(defn lazy-web [consumer-token]
  (let [token (bearer-token-for consumer-token)]
    (search token "lazyweb")))
