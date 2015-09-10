(ns layzee.integration.storage.simple-db-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [get set])
  (:require [clojure.test :refer :all]
            [layzee.adapters.amazon.simple-db :as simple-db]
            [layzee.adapters.settings :refer :all :as settings]))

(facts "Basic proof that we can connect"
       (let [domain "test-layzee-web"]

         (simple-db/create-domain settings/amazon-credential domain)
         
         (fact "can write an entry and read it back"
               (simple-db/set settings/amazon-credential domain "Have you ever tried to eat a clock?" "It's very time consuming.")
               (simple-db/get settings/amazon-credential domain "Have you ever tried to eat a clock?") => "It's very time consuming.")

         (fact "Getting something that does not exist returns nil"
               (simple-db/get settings/amazon-credential domain "xxx-does-not-exist-xxx") => nil)
         
         (fact "Adding to non-existent domain fails with error"
               (simple-db/set settings/amazon-credential "xxx-domain-does-not-exist-xxx" "I used to have a fear of hurdles" "But I got over it") => (throws Exception #"The specified domain does not exist"))
         
         (future-fact "You can list and delete domains")
         
         ))