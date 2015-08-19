(ns layzee.adapters.twitter
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]))

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
   "Accept"        "application/json"})

(defn- bearer-token-for[consumer-token]
  (let [reply (http/post
               "https://api.twitter.com/oauth2/token"
               {
                :headers (headers consumer-token)
                :body "grant_type=client_credentials"})]
    (:access_token (json/read-str (:body reply) :key-fn keyword))))

(defn- search[bearer-token what log opts]
  (log opts)
  (let [how-many (or (:count opts) 15) tweet-filter (or (:filter opts) (fn[what] what))]
    (let [url (format "https://api.twitter.com/1.1/search/tweets.json?count=%s&q=%s" how-many (% what))]
      (log url)
      (let [reply (http/get url {:headers (bearer-auth bearer-token)})]
        (filter tweet-filter (:statuses (json/read-str (:body reply) :key-fn keyword)))))))

(def log ^{:private true}
     (fn [msg & args]
       (when settings/log?
         (println (str "[log] " (apply format (.replace (str msg) "%" "%%") args))))))

(defn lazy-web [consumer-token & opts]
  (let [token (bearer-token-for consumer-token)]
    (search token "#lazyweb" log (if (nil? opts) {} (first opts)))))

(defn- replies-for [bearer-token id]
  (let [url (format "https://api.twitter.com/1.1/conversation/show.json?id=%s" id)]
      (let [reply (http/get url {:headers (bearer-auth bearer-token)})]
        (:statuses (json/read-str (:body reply) :key-fn keyword)))))

(defn replies [consumer-token tweet-id & opts]
  (let [token (bearer-token-for consumer-token)]
    (replies-for token tweet-id)))
