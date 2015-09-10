(ns layzee.integration.storage.simple-db-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [cemerick.rummage :as sdb]
            [layzee.adapters.settings :refer :all :as settings]))

(defn- set[amazon-credential key value] # https://console.aws.amazon.com/iam/home?region=us-west-2#security_credential
  (let [client (sdb/create-client "your aws id" "your aws secret-key")]
    (client.
    ))
  
  
(facts "Basic proof that we can connect"
       (fact "can write an entry and read it back"
             
               )
         )