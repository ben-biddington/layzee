(ns layzee.adapters.twitter.nice-twitter
  (:use midje.sweet)
  (:require [layzee.adapters.twitter.api :as api]
            [layzee.adapters.amazon.dynamo-db :as db]))

(defn- simplify[tweet]
  "A smaller representation, since we only need parts of each tweet"
  {
   :id_str         (-> tweet :id_str) 
   :created_at     (-> tweet :created_at)
   :text           (-> tweet :text) 
   :favorite_count (-> tweet :favorite_count)
   :user           { :screen_name (-> tweet :user :screen_name) :profile_image_url (-> tweet :user :profile_image_url)}})

(def ^{:private true} api-hit-count (atom 0))

(defn get-tweet [amazon-credential database-name oauth-credential]
     #(or
       (when-let [cached (db/get amazon-credential database-name %)] cached)
       (do
         (let [fresh-value (simplify (api/get-tweet oauth-credential %))]
           (swap! api-hit-count inc)
           (db/set amazon-credential database-name % fresh-value)
           fresh-value))))