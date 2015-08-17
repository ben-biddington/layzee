(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]))

(defn- env[name] (or (System/getenv name) "UNKNOWN"))
(def ^{:private true} consumer-token
     {:key (env "TWITTER_CONSUMER_KEY") :secret (env "TWITTER_CONSUMER_SECRET")})

(facts
 (fact "Can read the timeline"
       (let [result (twitter/lazy-web consumer-token)]
         (< 0 (count result)) => true)))

(facts
 (fact "Each tweet contains the tag #lazyweb"
       (let [result (first (twitter/lazy-web consumer-token))]
         (.contains (:text result) "#lazyweb") => true)))
