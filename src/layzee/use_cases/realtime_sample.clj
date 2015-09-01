(ns layzee.use-cases.realtime-sample
  (:require [layzee.adapters.journal :as journal]))

(defn- clean[text] (.replace text "\n" ""))

(defn- console[tweet]
  (let [text (clean (get tweet "text")) created-at (get tweet "created_at")]
    (when (not (nil? text))
      (println (format "[%s] -- %s" created-at text)))))

(defn- record[tweet]
  (let [text (clean (get tweet "text")) created-at (get tweet "created_at") screen-name (get-in tweet ["user" "screen_name"])]
    (when (not (nil? text))
      (journal/record (format "[%s] -- @%s -- %s" created-at screen-name text)))))

(defn- view[tweet]
  (console tweet)
  (record tweet))

(defn run[adapters]
  (apply (:realtime-fn adapters) [#(view %)]))

