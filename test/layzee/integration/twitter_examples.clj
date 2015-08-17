(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]))

(defn- fail[reason & args]
  ((throw (Exception. (apply format reason args)))))
  
(defn- env[name] (or (System/getenv name) (fail "You need to supply the <%s> environment variable" name)))

(def ^{:private true} consumer-token
     {:key (env "TWITTER_CONSUMER_KEY") :secret (env "TWITTER_CONSUMER_SECRET")})

(facts
 (fact "Can read the timeline (and the oage size is 15)"
       (let [result (twitter/lazy-web consumer-token)]
         (count result) => 15)))

(facts
 (fact "Each tweet contains the tag #lazyweb"
       (let [result (first (twitter/lazy-web consumer-token))]
         (.contains (:text result) "#lazyweb") => true)))
