(ns layzee.integration.lazy-web-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [time])
  (:require [clojure.test :refer :all]
            [layzee.timing :refer :all :as timing]
            [layzee.adapters.twitter.search :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]))

(facts
 (fact "Can read the timeline (and the page size is 15)"
       (let [result (twitter/lazy-web settings/twitter-bearer-token)]
         (count result) => 15)))
