(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]))

(facts
 (fact "Can read the timeline"
       (let [result (twitter/lazy-web)]
         (< 0 (count result)) => true)))
