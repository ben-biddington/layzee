(ns layzee.adapters.twitter.firehose
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :refer :all]
            [bone.signature-base-string :as signature-base-string]
            [bone.signature :as signature]))

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
    (signature/hmac-sha1-sign (signature-base-string/signature-base-string parameters) (str (:consumer-secret oauth-credential) "&" (:token-secret oauth-credential)))))

(defn- connect-core[oauth-credential]
  (let [url "https://stream.twitter.com/1.1/statuses/firehose.json"]
    (log (oauth-sign url oauth-credential))
    (http/get url {:headers {"Authorization" (oauth-sign url oauth-credential)} })))

(defn connect[oauth-credential]
  (connect oauth-credential))