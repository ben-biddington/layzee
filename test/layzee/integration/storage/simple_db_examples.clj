(ns layzee.integration.storage.simple-db-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [get set])
  (:require [clojure.test :refer :all]
            [layzee.adapters.amazon.simple-db :as simple-db]
            [layzee.adapters.settings :refer :all :as settings]))

(facts "Basic proof that we can connect"
       (let [domain "test-layzee-web" amazon-credential settings/amazon-credential]

         (simple-db/create-domain amazon-credential domain)
         
         (fact "can write an entry and read it back"
               (simple-db/set amazon-credential domain "Have you ever tried to eat a clock?" "It's very time consuming.")
               (simple-db/get amazon-credential domain "Have you ever tried to eat a clock?") => "It's very time consuming.")

         (fact "Getting something that does not exist returns nil"
               (simple-db/get amazon-credential domain "xxx-does-not-exist-xxx") => nil)
         
         (fact "Adding to non-existent domain fails with error"
               (simple-db/set amazon-credential "xxx-domain-does-not-exist-xxx" "I used to have a fear of hurdles" "But I got over it") => (throws Exception #"The specified domain does not exist"))
         
         (fact "You can list domains"
               (simple-db/list-domains amazon-credential) => (contains domain) )
         
         ))

(facts "About deleting domains"
       (fact "after deleting they are not listed"
             (let [name "party-bangers-database"]
               (simple-db/create-domain amazon-credential name)
               (simple-db/list-domains amazon-credential) => (contains name)
               (simple-db/delete-domain amazon-credential name)
               (simple-db/list-domains amazon-credential) =not=> (contains name)))
       )