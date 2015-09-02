(ns layzee.adapters.twitter.replies
  (:refer-clojure :exclude[filter])
  (:require 
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :refer :all]
            [clj-http.client :as http]
            [clj-http.util :as util]))

(defn to[tweet]
  (let [url (format "https://twitter.com/%s/status/%s.html" (:screen-name tweet) (:id tweet))]
    (let [reply (:body (http/get url {:headers {"User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"} }))]
      (let [match-seq (re-seq #".+associated-tweet-id=\"(.+)\".+" reply)]
        (map (fn[match] (let [[_ id] match] id)) match-seq)))))