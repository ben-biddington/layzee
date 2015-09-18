(ns layzee.integration.conversation-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer [write-str]]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.timing :as timing]
            [layzee.adapters.twitter.api :as api]
            [layzee.adapters.twitter.nice-twitter :as nice-api]
            [layzee.adapters.twitter.search :as search]
            [layzee.adapters.twitter.conversation :as conversation]
            [layzee.adapters.logging :refer :all]
            [layzee.adapters.amazon.dynamo-db :as db]))

(facts "Find entire conversations like this" ;; https://dev.twitter.com/rest/public/search; https://dev.twitter.com/rest/reference/get/search/tweets
       (let [result (conversation/for #(api/get-tweet settings/oauth-credential %) "636840679272873984")]
         
         (fact "it returns the tweet"
               (:id_str result) => "636840679272873984"
               (:text result)   => "@benbiddington lolwut?! http://t.co/AC0WvYYfOI")

         (fact "and its replies"
               (map #(:text %) (:replies result)) => (contains #{"@iamkey Got latest?" "@benbiddington have just now. Still failing." "@iamkey Config file format has changed &lt;https://t.co/66dquz6N78&gt; (soz)" "@benbiddington chur"})
         
               )))

(facts :wip "How does it perform?"
       (fact "for example"
             (timing/time
              (fn[] (conversation/for #(api/get-tweet settings/oauth-credential %) "636840679272873984"))
              #(println (format "It took <%sms> to find replies" (:duration %))))))

(facts "Find conversations from either cache or api"
       (db/new-table settings/amazon-credential "test-layzee-web")
       
       (let [result (conversation/for (nice-api/get-tweet settings/amazon-credential "test-layzee-web" settings/oauth-credential) "636840679272873984")]
         
         (fact "it returns the tweet"
               (:id_str result) => "636840679272873984"
               (:text result)   => "@benbiddington lolwut?! http://t.co/AC0WvYYfOI")

         (fact "and its replies"
               (map #(:text %) (:replies result)) => (contains #{"@iamkey Got latest?" "@benbiddington have just now. Still failing." "@iamkey Config file format has changed &lt;https://t.co/66dquz6N78&gt; (soz)" "@benbiddington chur"})
         
               ))
       )

