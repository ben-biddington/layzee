(ns layzee.adapters.twitter.firehose
  (:refer-clojure :exclude[filter])
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

(def ^{:private true} current (atom ""))
(def ^{:private true} syncRoot (Object.))

(defn- fail[reason & args]
  ((throw (Exception. (apply format reason args)))))

(defn- notify[callback args]
  (locking syncRoot
    (swap! current str args)
    
    (when (.contains args "\r\n")
      (do
        (let [t @current]
          (if-not (clojure.string/blank? t)
                  (apply callback [(json/read-str @current)])))
        (reset! current "")))))

(defn- listen[url verb headers body callback]
  "Listens to <url>, invoking <callback> each time a message arrives. 
   See: <http://neotyk.github.io/http.async.client/docs.html#sec-2-4-1>"
  
  (with-open [client (http-async/create-client)] ;; -> http://neotyk.github.io/http.async.client/docs.html
    (let [resp (http-async/stream-seq client verb url :headers headers :body body :timeout never)]
      (let [err (:error resp)]
        (if (realized? err)
          (fail "There was an error <%s>" err)
          (println "Connected and listenting")))

      (doseq [s (http-async/string resp)]
        (let [status @(:status resp)]
          (when (not (nil? status))
            (when (not (= 200 (:code status)))
              (fail "An error occured, status was <%s, %s>" (:code status) (:msg status)))))
        
        (try
         (notify callback s)
         (catch Exception e (println (format "ERR: %s" (.getMessage e))))))

      (println "Waiting complete -- check status for errors: " @(:status resp) (http-async/string resp) ))))

(defn sample[oauth-credential callback] ;; https://dev.twitter.com/streaming/reference/get/statuses/sample
  (let [url "https://stream.twitter.com/1.1/statuses/sample.json"]
    (println (format "Connecting to <%s>" url))
    (listen
     url
     :get
     { "Authorization" (oauth/sign url "GET" {} oauth-credential)}
     {}
     callback)))

(defn filter
  ([oauth-credential callback]
     "Returns tweets that match one of a default set of <tracks>."
     (filter oauth-credential callback ["lazyweb" "kanye", "xero"]))
  
  ([oauth-credential callback tracks] ;; https://dev.twitter.com/streaming/reference/post/statuses/filter
     "Returns tweets that match one or more of <tracks>."
     (let [url "https://stream.twitter.com/1.1/statuses/filter.json" body { "track" (clojure.string/join "," (if (empty? tracks) ["lazyweb" "kanye"] tracks)) }]
       (println (format "Connecting to <%s> with body <%s> (See https://dev.twitter.com/streaming/reference/post/statuses/filter)" url body))
       (listen
        url
        :post
        { "Authorization" (oauth/sign url "POST" body oauth-credential)}
        body
        callback))))
  