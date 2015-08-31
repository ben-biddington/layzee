(ns layzee.use-cases.realtime-sample)

(defn- view[tweet]
  (let [text (get tweet "text") created-at (get tweet "created_at")]
    (when (not (nil? text))
      (println (format "[%s] -- %s" created-at text)))))

(defn run[adapters]
  (apply (:realtime-fn adapters) [#(view %)]))
