(ns layzee.core
  (:gen-class)
  (:refer-clojure :exclude[filter])
  (:require 
   [layzee.adapters.twitter.search :refer :all :as twitter]
   [layzee.adapters.twitter.stream.filter :refer :all :as filter]
   [layzee.adapters.settings :refer :all :as settings]
   [layzee.use-cases.lazy-web :as lazy-web]
   [layzee.use-cases.realtime-sample :as realtime-sample]))

(def ^{:private true} lazy-web-search
  (fn [opts]
    (twitter/lazy-web settings/oauth-credential opts)))

(defn- realtime-filter[args]
     (fn [callback]
       (filter/filter settings/oauth-credential callback args)))

(defn -main [& args]
  (if (< 0 (count args))
    (realtime-sample/run { :realtime-fn (realtime-filter (rest args))})
    (lazy-web/run        { :search-adapter-fn lazy-web-search })))
