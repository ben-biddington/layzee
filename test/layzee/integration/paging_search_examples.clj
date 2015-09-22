(ns layzee.integration.paging-search-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer [write-str]]
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

(defn- page
  ":fn-producer -- the source, :fn-stop -- how we tell we're finished, :result -- collects the results"
  ([fn-producer fn-stop result]
     (if (apply fn-stop [result])
       result
         (recur fn-producer fn-stop (concat (apply fn-producer []) result)))))

(facts "How to page arbitrary sequences"
       (fact "for example, collect 20 results 5 at a time"
             (let [result (page #(range 5) #(<= 20 (count %)) '())]
               (count result) => 20)))