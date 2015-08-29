(ns layzee.adapters.twitter.api
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.oauth :as oauth]
            [layzee.adapters.logging :refer :all]
            [bone.auth-header :as auth :refer :all]
            [bone.signature :as signature]
            [bone.timestamps :as ts]))

(defn get-tweet[oauth-credential id]
  (let [url "https://api.twitter.com/1.1/statuses/show.json"]
    (http/get (format "%s?id=%s" url id) {:headers { "Authorization" (oauth/sign url { "id" id } oauth-credential)} })))