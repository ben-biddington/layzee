(ns layzee.adapters.twitter.authentication.bearer-tokens
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.net :refer :all]))

(defn- sign [key secret] (%64 (str (% key) ":" (% secret)))) ;; -> https://dev.twitter.com/oauth/application-only

(defn- headers[consumer-key consumer-secret] {
   "Authorization" (str "Basic " (sign consumer-key consumer-secret))
   "Content-type" "application/x-www-form-urlencoded;charset=UTF-8"})

(defn bearer-token-for[key secret]
  "See https://dev.twitter.com/oauth/application-only"
  (let [reply (http/post
               "https://api.twitter.com/oauth2/token"
               {
                :headers (headers key secret)
                :body "grant_type=client_credentials"})]
    (:access_token (json/read-str (:body reply) :key-fn keyword))))


