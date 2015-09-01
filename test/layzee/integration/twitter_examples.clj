(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter.search :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]))

(facts
 (fact "Can read the timeline (and the page size is 15)"
       (let [result (twitter/lazy-web settings/oauth-credential)]
         (count result) => 15)))
