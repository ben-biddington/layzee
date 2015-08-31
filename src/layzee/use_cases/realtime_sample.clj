(ns layzee.use-cases.realtime-sample)

(defn- view[tweet]
  (let [text (get tweet "text")]
    (when (not (nil? text))
      (println text))))

(defn run[adapters]
  (apply (:realtime-fn adapters) [#(view %)]))
