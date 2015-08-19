(ns layzee.adapters.settings
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn- fail[reason & args]
  ((throw (Exception. (apply format reason args)))))

(defn- env?[name] (not (nil? (System/getenv name))))

(def settings-file-name ".twitter")
(defn exists? [] (.exists (io/as-file settings-file-name)))

(defn- from-disk []
  (when (exists?)
    (let [f (slurp settings-file-name)]
      (json/read-str f :key-fn keyword))))

(defn- env[name] (or (System/getenv name) )) ;; 

(defn- from-env[] { :key (env "TWITTER_CONSUMER_KEY")  :secret (env "TWITTER_CONSUMER_SECRET")} )

(def consumer-token
     (or
      (from-disk)
      (from-env)
      (fail "You need to supply the <%s> environment variable, or the <%s> file" name settings-file-name)))

(def log? (env? "LOG"))