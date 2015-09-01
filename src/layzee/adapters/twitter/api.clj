(ns layzee.adapters.twitter.api
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json :refer [read-str]]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.oauth :as oauth]
            [layzee.adapters.logging :refer :all]
            [bone.auth-header :as auth :refer :all]
            [bone.signature :as signature]
            [bone.timestamps :as ts]))

(defn get-tweet[oauth-credential id]
  (let [url "https://api.twitter.com/1.1/statuses/show.json"]
    (let [reply (http/get (format "%s?id=%s" url id) {:headers { "Authorization" (oauth/sign url "GET" { "id" id } oauth-credential)} })]
      (json/read-str (:body reply) :key-fn keyword))))