(ns layzee.adapters.twitter
  (:require [clj-http.client :as http]
            [clj-http.util :as util]))

;; https://dev.twitter.com/oauth/application-only
;; https://github.com/dakrone/clj-http
;; https://apps.twitter.com/app/8673064

(defn- %[what] (util/url-encode what))
(defn- %64[what] (util/base64-encode (util/utf8-bytes what)))

(defn sign[consumer-token]
  (let [{key :key secret :secret} consumer-token]
    (%64 (str (% key) ":" (% secret))))) ;; -> https://dev.twitter.com/oauth/application-only

(defn- headers[consumer-token]
  {
   "Authorization" (str "Basic " (sign consumer-token))
   "Content-type" "application/x-www-form-urlencoded;charset=UTF-8"})

(defn- bearer-token[consumer-token]
  (println (headers consumer-token))
  (let [reply (http/post
               "https://api.twitter.com/oauth2/token"
               {
                :headers (headers consumer-token)
                :body "grant_type=client_credentials"})]
    (println reply)
    ))

(defn- env[name] (or (System/getenv name) "UNKNOWN")) 

(defn lazy-web []
  (println (str "KEY:" (env "TWITTER_CONSUMER_KEY")))
  (println (str "SECRET:" (env "TWITTER_CONSUMER_SECRET")))
  
  (println (bearer-token {:key (env "TWITTER_CONSUMER_KEY") :secret (env "TWITTER_CONSUMER_SECRET")}))
  ;;(let [reply ()]
  ;;  (println reply))
  [0])
