(ns layzee.adapters.settings
  (:gen-class)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]))

(defn- fail[reason & args]
  ((throw (Exception. (apply format reason args)))))
  
(defn- env[name] (or (System/getenv name) (fail "You need to supply the <%s> environment variable" name)))

(def consumer-token
     {:key (env "TWITTER_CONSUMER_KEY") :secret (env "TWITTER_CONSUMER_SECRET")})