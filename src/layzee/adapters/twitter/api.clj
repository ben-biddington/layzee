(ns layzee.adapters.twitter.api
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json :refer [read-str]]
            [layzee.adapters.oauth :as oauth]))

(defn get-tweet[oauth-credential id]
  (let [url "https://api.twitter.com/1.1/statuses/show.json"]
    (let [reply (http/get (format "%s?id=%s" url id) {:headers { "Authorization" (oauth/sign url "GET" { "id" id } oauth-credential)} :throw-exceptions false})]
      (let [status (:status reply)]
        (if (= 404 status)
          nil
          (json/read-str (:body reply) :key-fn keyword))))))