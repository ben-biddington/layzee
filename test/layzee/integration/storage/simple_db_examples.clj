(ns layzee.integration.storage.simple-db-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [get set])
  (:require [clojure.test :refer :all]
            [cemerick.rummage :as sdb]
            [cemerick.rummage.encoding :as enc]
            [layzee.adapters.settings :refer :all :as settings]))

;; https://github.com/cemerick/rummage
(defn- client-for[amazon-credential] (sdb/create-client (-> amazon-credential :access-key-id) (-> amazon-credential :secret-access-key)))
(defn- config-for[amazon-credential] (assoc enc/keyword-strings :client (client-for amazon-credential)))

(defn- set[amazon-credential domain key value] ;; https://console.aws.amazon.com/iam/home?region=us-west-2#security_credential
  (let [config (config-for amazon-credential)]
    (sdb/put-attrs config domain {::sdb/id key :name "value" :key value})))

(defn- get[amazon-credential domain key] 
  (let [config (config-for amazon-credential)]
    (-> (sdb/get-attrs config domain key) :key)))

(facts "Basic proof that we can connect"
       (let [domain "test-layzee-web"]
         (sdb/create-domain (config-for settings/amazon-credential) domain)
         
         (fact "can write an entry and read it back"
               (set settings/amazon-credential domain "Have you ever tried to eat a clock?" "It's very time consuming.")
               (get settings/amazon-credential domain "Have you ever tried to eat a clock?") => "It's very time consuming.")

         (fact "Getting something that does not exist returns nil"
               (get settings/amazon-credential domain "xxx-does-not-exist-xxx") => nil)
         
         (fact "Adding to non-existent domain fails with error"
               (set settings/amazon-credential "xxx-domain-does-not-exist-xxx" "I used to have a fear of hurdles" "But I got over it") => (throws Exception #"The specified domain does not exist"))
         
         (future-fact "You can list and delete domains")
         
         ))