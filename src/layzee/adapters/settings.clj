(ns layzee.adapters.settings
  (:gen-class)
  (:require [clojure.test :refer :all]))

(defn- fail[reason & args]
  ((throw (Exception. (apply format reason args)))))

(defn- env?[name] (not (nil? (System/getenv name))))

(defn- env[name] (or (System/getenv name) (fail "You need to supply the <%s> environment variable" name)))

(def consumer-token
     {
      :key (env "TWITTER_CONSUMER_KEY")
      :secret (env "TWITTER_CONSUMER_SECRET")})

(def log? (env? "LOG"))