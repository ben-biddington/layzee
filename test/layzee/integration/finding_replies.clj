(ns layzee.integration.finding-replies
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer [write-str]]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.twitter.api :as api]
            [layzee.adapters.twitter.search :as search]
            [layzee.adapters.twitter.replies :as replies]
            [layzee.adapters.logging :refer :all]))

(facts ;; https://dev.twitter.com/rest/public/search; https://dev.twitter.com/rest/reference/get/search/tweets
 
 (fact "Though searching by tweet id returns nothing" ;; https://twitter.com/benbiddington/status/636787150009184256
       (count (search/by-keyword settings/oauth-credential "636787150009184256")) => 0)
 
 (fact "Searching for something common returns some results"
       (let [result (search/by-keyword settings/oauth-credential "twitter")]
         (empty? result) => false)))

(facts
 (fact "Can find replies like this"
       (let [result (replies/to {:id "636787150009184256" :screen-name "benbiddington"})]
         (empty? result) => false
       )))
