(ns layzee.use-cases.realtime-sample
  (:require [layzee.adapters.journal :as journal]))

(defn- clean[text] (.replace text "\n" ""))

(defn- hash-tags[tweet]
  ;;(println (get-in tweet ["entities" "hashtags"]))
  (clojure.string/join "," (map #(str "#" (get % "text")) (or (get-in tweet ["entities" "hashtags"]) []))))

(defn- log-with[tweet fn]
  (let [text (clean (get tweet "text")) created-at (get tweet "created_at") screen-name (get-in tweet ["user" "screen_name"]) hash-tags (hash-tags tweet)]
    (when (not (nil? text))
      (apply fn [(format "[%s] -- @%s -- [%s] %s" created-at screen-name hash-tags text)]))))  
  
(defn- console[tweet] (log-with tweet println))
(defn- record[tweet] (log-with tweet journal/record))

(defn- view[tweet]
  (console tweet)
  (record tweet))

(defn run[adapters]
  (apply (:realtime-fn adapters) [#(view %)]))

