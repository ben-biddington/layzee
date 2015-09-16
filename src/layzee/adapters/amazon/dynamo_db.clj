(ns layzee.adapters.amazon.dynamo-db
  (:refer-clojure :exclude [get set])
  (:require [again.core :as again]
            [taoensso.faraday :as far]))
;; Console: https://console.aws.amazon.com/dynamodb/home?region=us-east-1

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

