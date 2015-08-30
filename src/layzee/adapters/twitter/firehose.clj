(ns layzee.adapters.twitter.firehose
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :refer :all]
            [layzee.adapters.oauth :as oauth]
            [bone.auth-header :as auth :refer :all]
            [bone.signature :as signature]
            [bone.timestamps :as ts]
            [http.async.client :as http-async]))

(defn- connect-core[oauth-credential]
  (let [url "https://stream.twitter.com/1.1/statuses/firehose.json"]
    (http/get url {:headers { "Authorization" (oauth/sign url {} oauth-credential)} })))

(defn connect[oauth-credential]
  (connect-core oauth-credential))

(def ^{:private true} never -1) 

(defn listen[url headers callback]
  "Listens to <url>, invoking <callback> each time a message arrives. 
   See: <http://neotyk.github.io/http.async.client/docs.html#sec-2-4-1>"
  (with-open [client (http-async/create-client)]
    (let [resp (http-async/stream-seq client :get url :headers headers :timeout never)]
      (doseq [s (http-async/string resp)]
        (apply callback [s])))))

(defn sample[oauth-credential] ;; https://dev.twitter.com/streaming/reference/get/statuses/sample
  (let [url "https://stream.twitter.com/1.1/statuses/sample.json"]
    (println "connecting")
    (listen
     url
     { "Authorization" (oauth/sign url {} oauth-credential)}
     #(println %))))
  