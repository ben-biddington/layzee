(ns layzee.adapters.twitter.conversation
  (:refer-clojure :exclude[get for])
  (:require 
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :refer :all]
            [layzee.adapters.twitter.search :refer :all :as twitter]
            [layzee.use-cases.lazy-web :as lazy-web]
            [layzee.adapters.twitter.replies :as replies]
            [layzee.adapters.twitter.api :as api]
            [clj-http.client :as http]
            [clj-http.util :as util]))

(defn- reply-ids[oauth-credential tweet]
  (replies/to {:id (-> tweet :id_str) :screen-name (-> tweet :user :screen_name)}))

(defn- full-replies-for[oauth-credential tweet]
  (pmap (partial api/get-tweet oauth-credential) (reply-ids oauth-credential tweet)))

(defn- assoc-replies-to[oauth-credential tweet]
  (assoc tweet :replies (full-replies-for oauth-credential tweet)))

(defn for[oauth-credential id]
  (let [tweet (api/get-tweet oauth-credential id)]
    (assoc-replies-to oauth-credential tweet)))