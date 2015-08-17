(ns layzee.adapters.twitter
  (:require [clj-http.client :as http]))

;; https://dev.twitter.com/oauth/application-only
;; https://github.com/dakrone/clj-http
;; https://apps.twitter.com/app/8673064

(defn- sign[consumer-token]
  (let [key :key secret :secret] consumer-token))

(defn- bearer-token[consumer-token]
  (sign consumer-token))

(defn lazy-web []
  (println (bearer-token {:key (System/getenv "TWITTER_CONSUMER_KEY") :secret (System/getenv "TWITTER_CONSUMER_SECRET")}))
  (let [reply (http/post (str "http://api.twitter.com/oauth2/token"))]
    (println reply))
  [0])
