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

(def ^{:private true} api-hit-count (atom 0))

(defn get-tweet
  ([amazon-credential simple-db-domain oauth-credential] {:log #(%)})
  ([amazon-credential simple-db-domain oauth-credential opts]
  "Returns a tweet lookup function that looks first in db (using `simple-db-domain`) then on twitter. Populates db when required."
  #(or
    (let [log (:log opts)]
      (when-let [cached (db/get amazon-credential simple-db-domain %)]
        (apply log [(format "HIT: " %)])
        cached)
      (do
        (let [fresh-value (simplify (api/get-tweet oauth-credential %))]
          (swap! api-hit-count inc)
          (apply log [(format "MISS: " fresh-value)])
          (db/set amazon-credential simple-db-domain % fresh-value) ;; [!] Can only store 1024 bytes
          fresh-value))))))