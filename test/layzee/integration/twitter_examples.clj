(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.core :refer :all]))

(facts
 (fact "Can read the timeline"
       (+ 1 1) => 2))
