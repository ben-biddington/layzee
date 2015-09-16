(ns leiningen.clear
  "Clear the storage database of all tables"
  (:require [layzee.adapters.amazon.dynamo-db :as db]
            [layzee.adapters.settings :as settings]))

(defn clear [] 
  (db/delete-all-tables settings/amazon-credential))
