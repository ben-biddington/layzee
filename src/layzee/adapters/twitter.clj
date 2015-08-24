(ns layzee.adapters.twitter
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [bone.signature-base-string :as signature-base-string]
            [bone.signature :as signature]))

;; https://dev.twitter.com/oauth/application-only
;; https://github.com/dakrone/clj-http
;; https://apps.twitter.com/app/8673064

(defn- %[what] (util/url-encode what))
(defn- %64[what] (util/base64-encode (util/utf8-bytes what)))

(defn- sign[key secret]
    (%64 (str (% key) ":" (% secret)))) ;; -> https://dev.twitter.com/oauth/application-only

(defn- headers[consumer-key consumer-secret]
  {
   "Authorization" (str "Basic " (sign consumer-key consumer-secret))
   "Content-type" "application/x-www-form-urlencoded;charset=UTF-8"})

(defn- bearer-auth[token]
  {
   "Authorization" (str "Bearer " token)
   "Accept"        "application/json"})

(defn- bearer-token-for[key secret]
  (let [reply (http/post
               "https://api.twitter.com/oauth2/token"
               {
                :headers (headers key secret)
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

(defn lazy-web [oauth-credential & opts]
  (let [token (bearer-token-for (:consumer-key oauth-credential) (:consumer-secret oauth-credential))]
    (search token "#lazyweb" log (if (nil? opts) {} (first opts)))))

(defn- replies-for [bearer-token id]
  (let [url (format "https://api.twitter.com/1.1/conversation/show.json?id=%s" id)]
      (let [reply (http/get url {:headers (bearer-auth bearer-token)})]
        (:statuses (json/read-str (:body reply) :key-fn keyword)))))

(defn replies [oauth-credential tweet-id & opts]
  (let [token (bearer-token-for (:consumer-key oauth-credential) (:consumer-secret oauth-credential))]
    (replies-for token tweet-id)))

(defn param[name,value] (struct signature-base-string/parameter name value))

(defn- oauth-sign[url oauth-credential]
  (let [parameters {
    :verb "GET"
    :url url
    :parameters (list 
      (param "oauth_consumer_key"     (:consumer-key oauth-credential))
      (param "oauth_token"            (:token-key oauth-credential))
      (param "oauth_timestamp"        "1191242096")
      (param "oauth_nonce"            "kllo9940pd9333jh")
      (param "oauth_signature_method" "HMAC-SHA1")
      (param "oauth_version"          "1.0"))}]
    (signature-base-string/signature-base-string parameters)))

(defn- connect[oauth-credential]
  (let [url "https://stream.twitter.com/1.1/statuses/firehose.json"]
    (http/get url {:headers {"Authorization" (oauth-sign url oauth-credential)} })))

(defn stream-connect[oauth-credential]
  (connect oauth-credential))
