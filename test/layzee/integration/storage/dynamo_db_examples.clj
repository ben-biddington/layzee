(ns layzee.integration.storage.dynamo-db-examples
  (:use midje.sweet)
  (:refer-clojure :exclude [get set])
  (:require [clojure.test :refer :all]
            [layzee.adapters.settings :refer :all :as settings]
            [taoensso.faraday :as far]))

(defn- client-opts[amazon-credential]
  {
   :access-key (:access-key-id amazon-credential)
   :secret-key (:secret-access-key amazon-credential)

   ;;; You may optionally override the default endpoint if you'd like to use DDB
   ;;; Local or a different AWS Region (Ref. http://goo.gl/YmV80o), etc.:
   ;; :endpoint "http://localhost:8000"                   ; For DDB Local
   ;; :endpoint "http://dynamodb.eu-west-1.amazonaws.com" ; For EU West 1 AWS region
  })

(defn list-tables[amazon-credential]
  (far/list-tables (client-opts amazon-credential)))

(defn- table-exists?[amazon-credential name]
  (not (nil? (far/describe-table (client-opts amazon-credential) name))))

(defn new-table[amazon-credential name]
  (far/ensure-table (client-opts amazon-credential) name
                    [:id :s] 
                    {:throughput {:read 1 :write 1} :block? true }))

(defn delete-table[amazon-credential name]
  (when (table-exists? amazon-credential name)
    (far/delete-table (client-opts amazon-credential) name)))

(defn delete-all-tables[amazon-credential]
  (doseq [name (list-tables amazon-credential)]
    (delete-table amazon-credential name)))

(defn set[amazon-credential table-name name value]
  (far/put-item (client-opts amazon-credential) (symbol table-name) { :id name :data (far/freeze value)} ))

(defn get[amazon-credential table-name name]
  (:data (far/get-item (client-opts amazon-credential) (symbol table-name) { :id name } { :attrs [:data] :consistent? true} )))

(def test-table-name "lazy-web-test")

(facts "Can add items to a table"
       (new-table settings/amazon-credential test-table-name)
       
       (future-fact "You can only add maps -- NOT strings for example")
       
       (fact "for example"
             (set settings/amazon-credential test-table-name "1" {:text "The tweet goes here"})
             (get settings/amazon-credential test-table-name "1") => {:text "The tweet goes here"}))