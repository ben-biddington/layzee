(ns layzee.integration.storage.dynamo-db-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [get set])
  (:require [clojure.test :refer :all]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.amazon.dynamo-db :as dynamo-db]))

(def test-table-name "layzee-web-test")

(facts "Can add items to a table"
       (dynamo-db/new-table settings/amazon-credential test-table-name)
       
       (future-fact "You can only add maps -- NOT strings for example")
       
       (fact "for example"
             (dynamo-db/set settings/amazon-credential test-table-name "1" {:text "The tweet goes here"})
             (dynamo-db/get settings/amazon-credential test-table-name "1") => {:text "The tweet goes here"}))