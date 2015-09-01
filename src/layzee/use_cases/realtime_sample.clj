(ns layzee.use-cases.realtime-sample
  (:require [layzee.adapters.journal :as journal]))

(defn- console[tweet]
  (let [text (get tweet "text") created-at (get tweet "created_at")]
    (when (not (nil? text))
      (println (format "[%s] -- %s" created-at text)))))

(defn- record[tweet]
  (let [text (get tweet "text") created-at (get tweet "created_at")]
    (when (not (nil? text))
      (journal/record (format "[%s] -- %s" created-at text)))))

(defn- view[tweet]
  (console tweet)
  (record tweet))

(defn run[adapters]
  (apply (:realtime-fn adapters) [#(view %)]))

