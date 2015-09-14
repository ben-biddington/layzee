(ns layzee.integration.storage.simple-db-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [get set])
  (:require [clojure.test :refer :all]
            [layzee.adapters.amazon.simple-db :as simple-db]
            [layzee.adapters.settings :refer :all :as settings]))

(def domain "test-layzee-web")

(background
        (before :facts
                (do 
                  (simple-db/delete-domain amazon-credential domain)
                  (simple-db/create-domain amazon-credential domain))))

(facts "Basic proof that we can store things in simpledb"
       (fact "can write an entry and read it back"
             (simple-db/set amazon-credential domain "Have you ever tried to eat a clock?" "It's very time consuming.")
             (simple-db/get amazon-credential domain "Have you ever tried to eat a clock?") => "It's very time consuming.")

       (fact "Getting something that does not exist returns nil"
             (simple-db/get amazon-credential domain "xxx-does-not-exist-xxx") => nil)
       
       (future-fact "Adding to non-existent domain fails with error"
             (simple-db/set amazon-credential "xxx-domain-does-not-exist-xxx" "I used to have a fear of hurdles" "But I got over it") => (throws Exception #"The specified domain does not exist"))
         
       (fact "You can list domains"
             (simple-db/list-domains amazon-credential) => (contains domain) )

       (future-fact "Do we store nil values? Nil keys?")
       (future-fact "Does setting twice overwrite?"))

(facts "What certain types look like after round trip"
       (fact "A hashmap is returned as a hashmap"
             (simple-db/set amazon-credential domain "a-hashmap-example" {:id_str 636843117526671361, :text "@benbiddington have just now. Still failing.", :favorite_count 0, :user {:screen_name "iamkey"}})
             (simple-db/get amazon-credential domain "a-hashmap-example") => {:id_str 636843117526671361, :text "@benbiddington have just now. Still failing.", :favorite_count 0, :user {:screen_name "iamkey"}})

       (fact "[!] But a hashmap with string keys is returned with keyword keys"
             (simple-db/set amazon-credential domain "a-hashmap-example-with-string-keys" {"id_str" 636843117526671361, "text" "@benbiddington have just now. Still failing.", "favorite_count" 0, "user" {:screen_name "iamkey"}})
             (simple-db/get amazon-credential domain "a-hashmap-example-with-string-keys") => {:id_str 636843117526671361, :text "@benbiddington have just now. Still failing.", :favorite_count 0, :user {:screen_name "iamkey"}})
       
       (fact "A vector is returned as a vector"
             (simple-db/set amazon-credential domain "a-vector-example" ["a" "b" "c" "d"])
             (simple-db/get amazon-credential domain "a-vector-example") => ["a" "b" "c" "d"]))

(facts "About deleting domains"
       (fact "after deleting they are not listed"
             (let [name "party-bangers-database"]
               (simple-db/create-domain amazon-credential name)
               (simple-db/list-domains amazon-credential) => (contains name)
               (simple-db/delete-domain amazon-credential name)
               (simple-db/list-domains amazon-credential) =not=> (contains name))))