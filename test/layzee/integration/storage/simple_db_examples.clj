(ns layzee.integration.storage.simple-db-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [get set])
  (:require [clojure.test :refer :all]
            [cemerick.rummage :as sdb]
            [cemerick.rummage.encoding :as enc]
            [layzee.adapters.settings :refer :all :as settings]))

(defn- set[amazon-credential key value] ;; https://console.aws.amazon.com/iam/home?region=us-west-2#security_credential
  (let [client (sdb/create-client (-> amazon-credential :access-key-id) (-> amazon-credential :secret-access-key))]
    (let [config (assoc enc/keyword-strings :client client)]
      (sdb/create-domain config "bjb-demo")
      (sdb/put-attrs config "bjb-demo" {::sdb/id key :name "value" :key value}))))

(defn- get[amazon-credential key] 
  (let [client (sdb/create-client (-> amazon-credential :access-key-id) (-> amazon-credential :secret-access-key))]
    (let [config (assoc enc/keyword-strings :client client)]
      (sdb/create-domain config "bjb-demo")
      (-> (sdb/get-attrs config "bjb-demo" key) :key))))
  
(facts "Basic proof that we can connect"
       (fact "can write an entry and read it back"
             (set settings/amazon-credential "Have you ever tried to eat a clock?" "It's very time consuming.")
             (get settings/amazon-credential "Have you ever tried to eat a clock?") => "It's very time consuming.")

       (future-fact "Getting something that does not exist returns nil")
       
         )