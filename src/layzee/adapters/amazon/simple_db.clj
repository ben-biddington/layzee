(ns layzee.adapters.amazon.simple-db
  (:refer-clojure :exclude [get set])
  (:require [cemerick.rummage :as sdb]
            [cemerick.rummage.encoding :as enc]))

;; https://github.com/cemerick/rummage
(defn- client-for[amazon-credential] (sdb/create-client (-> amazon-credential :access-key-id) (-> amazon-credential :secret-access-key)))
(defn- config-for[amazon-credential] (assoc enc/keyword-strings :client (client-for amazon-credential)))

(defn- ex[amazon-credential fn] (apply fn (config-for amazon-credential) []))

(defn- as-admin[amazon-credential fn] (apply fn (client-for amazon-credential) []))

(defn set[amazon-credential domain key value] ;; https://console.aws.amazon.com/iam/home?region=us-west-2#security_credential
  (ex amazon-credential #(sdb/put-attrs % domain {::sdb/id key :name "value" :key value})))

(defn get[amazon-credential domain key] 
  (ex amazon-credential #(-> (sdb/get-attrs % domain key) :key)))

(defn create-domain[amazon-credential name]
  (ex amazon-credential #(sdb/create-domain % name)))

(defn list-domains[amazon-credential]
  (as-admin amazon-credential #(sdb/list-domains %)))

(defn delete-domain[amazon-credential name]
  (as-admin amazon-credential #(sdb/delete-domain % name)))

