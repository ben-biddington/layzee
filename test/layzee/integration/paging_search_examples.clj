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
       (fact "for example, searching for mentions in small pages"
             (let [search-results (search/by-keyword settings/twitter-bearer-token "@iamkey" {:count 20 :page-size 5})]
               (println search-results)
               (count search-results) => 20)
             )
       )