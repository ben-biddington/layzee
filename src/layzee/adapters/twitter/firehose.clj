(ns layzee.adapters.twitter.firehose
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :refer :all]
            [layzee.adapters.oauth :as oauth]
            [bone.auth-header :as auth :refer :all]
            [bone.signature :as signature]
            [bone.timestamps :as ts]))

(defn- connect-core[oauth-credential]
  (let [url "https://stream.twitter.com/1.1/statuses/firehose.json"]
    (http/get url {:headers { "Authorization" (oauth/sign url {} oauth-credential)} })))

(defn connect[oauth-credential]
  (connect-core oauth-credential))