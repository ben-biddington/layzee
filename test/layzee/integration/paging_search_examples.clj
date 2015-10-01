(ns layzee.integration.paging-search-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer [write-str]]
            [layzee.paging :as paging]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.twitter.api :as api]
            [layzee.adapters.twitter.search :as search]
            [layzee.adapters.twitter.replies :as replies]
            [layzee.adapters.logging :refer :all]))

(facts "How to collect search results in pages (provided page-size > 1)"
       (let [search-results (search/by-keyword-paged settings/twitter-bearer-token "@iamkey" {:limit 5 :page-size 2})]
         (fact "it returns the requested count"
               (count search-results) => 5)

         (fact "it returns no duplicates"
               (count (distinct (map #(:id %) search-results))) => 5)))

(facts "Quirks"
       (fact "because max_id returns tweets with id <= whatever value you supply (https://dev.twitter.com/rest/public/timelines), when you use age size 1 you always get the same one back"
             (let [search-results (search/by-keyword-paged settings/twitter-bearer-token "@iamkey" {:limit 3 :page-size 1})]
               (println (map #(:id %) search-results))
               (count search-results) => 3
               (count (distinct (map #(:id %) search-results))) => 1)))

(facts "How to page arbitrary sequences"
       (fact "for example, collect 20 results 5 at a time"
             (let [result (paging/page #(range 5) #(<= 20 (count %)) '())]
               (count result) => 20)))

(defn- source[args] (range 5))

(defn- source-args[result args] (update-in args [:page] inc))

(facts "You also need to be able to tailor the arguments to the next call -- or produce the next function to call"
       (fact "for example, collect 20 results 5 at a time"
             (let [result (paging/page source source-args #(<= 20 (count %)) '() {:page 1})]
               (count result) => 20)))