(ns layzee.use-cases.realtime-sample)

(defn run[adapters]
  (apply
   (:realtime-fn adapters)
   [{:callback #(println %)}]))
