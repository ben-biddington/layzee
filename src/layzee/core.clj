(ns layzee.core
  (:gen-class)
  (:require 
   [layzee.adapters.twitter.search :refer :all :as twitter]
   [layzee.adapters.settings :refer :all :as settings]
   [layzee.use-cases.lazy-web :refer :all :as lazy-web]))

(def ^{:private true} lazy-web-search
  (fn [opts]
    (twitter/lazy-web settings/oauth-credential opts)))

(defn -main [& args]
  (lazy-web/run {:search-adapter-fn lazy-web-search}))
