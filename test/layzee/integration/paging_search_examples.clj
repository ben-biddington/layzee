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

(facts "How to collect search results in pages"
       (future-fact "for example, searching for mentions in small pages"
             (let [search-results (search/by-keyword settings/twitter-bearer-token "@iamkey" {:limit 3 :page-size 1})]
               ;;(println search-results)
               (count search-results) => 20)))

(facts "How to page arbitrary sequences"
       (fact "for example, collect 20 results 5 at a time"
             (let [result (paging/page #(range 5) #(<= 20 (count %)) '())]
               (count result) => 20)))
(defn- page
  ":fn-producer -- function that produces the results
   :fn-args     -- function that produces arguments for the producer
   :fn-stop     -- how we tell we're finished
   :result      -- collects the results
   :args        -- [optional] arguments for :fn-producer. Defaults to empty map/vector"
  ([fn-producer fn-args fn-stop result args]
     (if (apply fn-stop [result])
       result
       (recur fn-producer fn-args fn-stop (concat (apply fn-producer [args]) result) (apply fn-args [result args])))))

(defn- source[args] (range 5))

(defn- source-args[result args] (update-in args [:page] inc))

(facts "You also need to be able to tailor the arguments to the next call -- or produce the next function to call"
       (fact "for example, collect 20 results 5 at a time"
             (let [result (page source source-args #(<= 20 (count %)) '() {:page 1})]
               (count result) => 20)))