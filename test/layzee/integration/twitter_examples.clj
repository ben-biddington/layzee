(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter.search :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]))

(facts
 (fact "Can read the timeline (and the page size is 15)"
       (let [result (twitter/lazy-web settings/oauth-credential)]
         (count result) => 15)))

(facts
 (fact "Each tweet contains the tag #lazyweb"
       (let [result (first (twitter/lazy-web settings/oauth-credential))]
         (.contains (:text result) "#lazyweb") => true)))

;(facts
; (fact "Get replies to a tweet like this"
;       (let [result (first (twitter/replies settings/consumer-token "633472387497902080"))]
;         (println result))))