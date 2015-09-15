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

(facts "How long does it take roughly?"
       (fact "to, for example, do a lazy web search"
       (let [result (time #(twitter/lazy-web settings/twitter-bearer-token))]
         (:duration result) => (roughly 2000 1000)
         (count (:result result)) => 15)))
