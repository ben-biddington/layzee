(ns layzee.adapters.twitter.replies
  (:refer-clojure :exclude[get])
  (:require 
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :refer :all]
            [clj-http.client :as http]
            [clj-http.util :as util]))

(def ^{:private true} headers {"User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36"})            
            
(defn- get[url]
  (let [reply (http/get url { :headers headers :throw-exceptions false } )]
    (let [status (:status reply)]
        (if (= 200 status) (:body reply) ""))))            
            
(defn to[tweet]
  (let [url (format "https://twitter.com/%s/status/%s.html" (:screen-name tweet) (:id tweet))
        reply (get url)]
      (filter #(not (= (:id tweet) %))
        (map #(let [[_ id] %] id) (re-seq #".+-tweet-id=\"(.+)\"" reply)))))