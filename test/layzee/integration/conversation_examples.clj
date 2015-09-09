(ns layzee.integration.conversation-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer [write-str]]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.twitter.api :as api]
            [layzee.adapters.twitter.search :as search]
            [layzee.adapters.twitter.conversation :as conversation]
            [layzee.adapters.logging :refer :all]))

(facts "Find entire conversations like this" ;; https://dev.twitter.com/rest/public/search; https://dev.twitter.com/rest/reference/get/search/tweets
       (let [result (conversation/for settings/oauth-credential "636840679272873984")]
         
         (fact "it returns the same tweet"
               (:id_str result) => "636840679272873984"
               (:text result)   => "@benbiddington lolwut?! http://t.co/AC0WvYYfOI")

         (fact "and it has the following replies"
               (map #(:text %) (:replies result)) => (contains #{"@iamkey Got latest?" "@benbiddington have just now. Still failing." "@iamkey Config file format has changed &lt;https://t.co/66dquz6N78&gt; (soz)" "@benbiddington chur"})
         
         )))

