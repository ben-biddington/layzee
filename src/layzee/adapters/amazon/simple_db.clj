(ns layzee.adapters.amazon.simple-db
  (:refer-clojure :exclude [get set])
  
  (:require [cemerick.rummage :as sdb]
            [cemerick.rummage.encoding :as enc]
            [clojure.data.json :as json]
            [again.core :as again]))

;; https://github.com/cemerick/rummage
(defn- client-for[amazon-credential] (sdb/create-client (-> amazon-credential :access-key-id) (-> amazon-credential :secret-access-key)))
(defn- config-for[amazon-credential] (assoc enc/keyword-strings :client (client-for amazon-credential)))

(def ^{:private true} exponential-backoff-strategy
  (again/max-duration
    10000
    (again/max-retries
      10
      (again/randomize-strategy
        0.5
        (again/multiplicative-strategy 500 1.5)))))

(defn- ex[amazon-credential fn]
  (again/with-retries exponential-backoff-strategy
                      (apply fn (config-for amazon-credential) [])))

(defn- as-admin[amazon-credential fn]
  (again/with-retries exponential-backoff-strategy
                      (apply fn (client-for amazon-credential) [])))

(defn encode[text] (json/write-str text))
(defn decode[text] (json/read-str text :key-fn keyword))

(defn set[amazon-credential domain key value] ;; https://console.aws.amazon.com/iam/home?region=us-west-2#security_credential
  (ex amazon-credential #(sdb/put-attrs % domain {::sdb/id key :name "value" :key (encode value)})))

(defn get[amazon-credential domain key] 
  (if-let [result (ex amazon-credential #(-> (sdb/get-attrs % domain key) :key))]
    (decode result)
    nil))

(defn create-domain[amazon-credential name]
  (ex amazon-credential #(sdb/create-domain % name)))

(defn list-domains[amazon-credential]
  (as-admin amazon-credential #(sdb/list-domains %)))

(defn delete-domain[amazon-credential name]
  (as-admin amazon-credential #(sdb/delete-domain % name)))

