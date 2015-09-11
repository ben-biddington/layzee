(ns layzee.adapters.twitter.nice-twitter
  (:use midje.sweet)
  (:require [layzee.adapters.twitter.api :as api]
            [layzee.adapters.amazon.simple-db :as db]))

(defn- simplify[tweet]
  "A smaller representation that will fit in SimpleDB (1024 B limit)"
  {
   :id_str         (-> tweet :id_str) 
   :text           (-> tweet :text) 
   :favorite_count (-> tweet :favorite_count)
   :user           { :screen_name (-> tweet :user :screen_name)}})

(defn get-tweet[amazon-credential simple-db-domain oauth-credential]
  "Returns a tweet lookup function that looks first in db (using `simple-db-domain`) then on twitter. Populates db when required."
     #(or
       ;(db/get amazon-credential simple-db-domain %) ;; does not work -- is returning format like #{[:user {:screen_name "iamkey"}] [:text "@benbiddington have just now. Still failing."] [:id_str 636843117526671361] [:favorite_count 0]} 
       (do
         (println "TWEET:" % " => " (db/get amazon-credential simple-db-domain %))
         (let [fresh-value (simplify (api/get-tweet oauth-credential %))]
           (println fresh-value) 
           (db/set amazon-credential simple-db-domain % fresh-value) ;; [!] Can only store 1024 bytes
           fresh-value))))