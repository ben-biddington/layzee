(ns layzee.core
  (:gen-class)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]))

(defn- clean[text]
  (.replace text "\n" ""))

(defn- view[t]
  (format "[%s] %s -- %s" (:created_at t) (-> t :text clean) (-> t :user :name)))


(defn -main [& args]
  (let [result (twitter/lazy-web settings/consumer-token)]
    (println (format "Searched for <#lazyweb> and found <%s> results" (count result)))

    (doseq [tweet result]
      (println (view tweet)))))
