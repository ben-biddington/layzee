(ns layzee.adapters.twitter.conversation
  (:refer-clojure :exclude[get for])
  (:require [layzee.adapters.twitter.replies :as replies]))

(defn- reply-ids[tweet]
  (replies/to {:id (-> tweet :id_str) :screen-name (-> tweet :user :screen_name)}))

(defn- full-replies-for[fn-to-get-a-tweet tweet]
  (pmap fn-to-get-a-tweet (reply-ids tweet)))

(defn- assoc-replies-to[fn-to-get-a-tweet tweet]
  (assoc tweet :replies (or (full-replies-for fn-to-get-a-tweet tweet) [])))

(defn for[fn-to-get-a-tweet id]
  (let [tweet (apply fn-to-get-a-tweet [id])]
    (assoc-replies-to fn-to-get-a-tweet tweet)))