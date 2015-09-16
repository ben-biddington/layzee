(ns layzee.integration.storage.dynamo-db-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [get set])
  (:require [clojure.test :refer :all]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.amazon.dynamo-db :as dynamo-db]))

(def test-table-name "layzee-web-test")

(facts "Can add items to a table"
       (dynamo-db/new-table settings/amazon-credential test-table-name)
       
       (fact "You can add items of any type"
             (dynamo-db/set settings/amazon-credential test-table-name "1" "value for id 1")
             (dynamo-db/get settings/amazon-credential test-table-name "1") => "value for id 1")

       (fact "You can even store nil"
             (dynamo-db/set settings/amazon-credential test-table-name "1" nil)
             (dynamo-db/get settings/amazon-credential test-table-name "1") => nil)
       
       (fact "for example"
             (dynamo-db/set settings/amazon-credential test-table-name "1" {:text "The tweet goes here"})
             (dynamo-db/get settings/amazon-credential test-table-name "1") => {:text "The tweet goes here"}))