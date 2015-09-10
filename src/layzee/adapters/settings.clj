(ns layzee.adapters.settings
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn- fail[reason & args]
  ((throw (Exception. (apply format reason args)))))

(defn- env?[name] (not (nil? (System/getenv name))))

(def ^{:private true} settings-file-name ".twitter")
(defn exists? [] (.exists (io/as-file settings-file-name)))

(defn- from-disk []
  (when (exists?)
    (json/read-str (slurp settings-file-name) :key-fn keyword)))

(defn- env[name] (System/getenv name))

(defn- from-env[] { :consumer-key (env "TWITTER_CONSUMER_KEY")  :consumer-secret (env "TWITTER_CONSUMER_SECRET")} )

(def oauth-credential
     (or
      (from-disk)
      (from-env)
      (fail "You need to supply the <%s> environment variable, or the <%s> file" name settings-file-name)))

(def log? (env? "LOG"))

(def amazon-credential
     (let [filename ".amazon"]
       (if (exists?)
         (json/read-str (slurp filename) :key-fn keyword)
         (fail "You need to supply amazon credentials in a file called <%s>" filename)))