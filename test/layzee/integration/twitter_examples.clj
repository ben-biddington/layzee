(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]))

(facts
 (fact "Can read the timeline (and the page size is 15)"
       (let [result (twitter/lazy-web settings/consumer-token)]
         (count result) => 15)))

(facts
 (fact "Each tweet contains the tag #lazyweb"
       (let [result (first (twitter/lazy-web settings/consumer-token))]
         (.contains (:text result) "#lazyweb") => true)))

(facts
 (fact "Each tweet contains its replies (?)"
       (let [result (first (twitter/lazy-web settings/consumer-token))]
         (println result)))