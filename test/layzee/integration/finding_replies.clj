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
       (count (search/by-keyword settings/twitter-bearer-token "636787150009184256")) => 0)
 
 (fact "Searching for something common returns some results"
       (let [result (search/by-keyword settings/twitter-bearer-token "twitter")]
         (empty? result) => false))

 (fact "Can find replies like this"
       (let [result (replies/to {:id "636787150009184256" :screen-name "benbiddington"})]
         (empty? result) => false))

 (fact "It returns all of the expected replies" ;; https://twitter.com/iamkey/status/636840679272873984
       (let [expected-count 4 result (replies/to {:id "636840679272873984" :screen-name "iamkey"})]
         (count result) => expected-count
         (count (distinct result)) => (count result) :notes ["Expected them to all be distinct"]))

 (fact "it returns empty when there *are* no replies" ;; https://twitter.com/benbiddington/status/638277898596540416
       (let [result (replies/to {:id "638277898596540416" :screen-name "iamkey"})]
         (count result) => 0))
         
 (fact "it returns empty when status id is bung"
       (let [result (replies/to {:id "xxx-this-is-bung-xxx" :screen-name "iamkey"})]
         (count result) => 0)))
         
(facts "Find full replies"
       (let [result (replies/to {:id "636840679272873984" :screen-name "iamkey"})]
         (fact "for example"
               (let [tweets (pmap #(api/get-tweet settings/oauth-credential %) result)]
                 (map #(:text %) tweets) => (contains #{"@iamkey Got latest?" "@benbiddington have just now. Still failing." "@iamkey Config file format has changed &lt;https://t.co/66dquz6N78&gt; (soz)" "@benbiddington chur"})))

         (future-fact "and each one has the same `in_reply_to_status_id`. Interestingly it FAILS because this is a CONVERSATION -- not replies to the same tweet. So they all have different `in_reply_to_status_id` values."
             (doseq [tweet tweets]
                  (:in_reply_to_status_id tweet) => 636840679272873984 :notes ["Expected all of the replies to be tagged as replies to the original"]))))

(defn replies-to[tweet] ;; also consider using the date it was posted so we can search "newer-than"
  (let [search-result (search/by-keyword settings/twitter-bearer-token (format "@%s" (:screen_name tweet)) {:count 1000}) id (:id_str tweet)]
    (println (map #(format "id: %s, in_reply_to: %s, %s\n" (:id_str %) (:in_reply_to_status_id_str %) (:text %) (:text %)) search-result))
    (println (count search-result))
    (filter #(= id (:in_reply_to_status_id_str %)) search-result)))

(facts :next "As an alternative to the (slow) page scrapes, we could instead do this" ;; https://www.quora.com/How-can-I-get-a-list-of-replies-to-a-specific-tweet-via-Twitter-API
       ;; Find tweets referenceing the author by searching for them
       ;; Find any that are in reply to that tweet
       ;; Note that we will have to page through the results <https://dev.twitter.com/rest/public/timelines>, since at the moment we get single page
       (let [result (replies-to { :id_str "636840679272873984" :screen_name "iamkey" })]
         (println result)
         
         )
       )