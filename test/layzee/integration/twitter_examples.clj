(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]))

(facts
 (fact "Can read the timeline"
       (let [result (twitter/lazy-web)]
         (< 0 (count result)) => true)))

(facts
 (fact "Each tweet contains the tag #lazyweb"
       (let [result (first (twitter/lazy-web))]
          (.contains "lazyweb" (:tags result)) => true)))