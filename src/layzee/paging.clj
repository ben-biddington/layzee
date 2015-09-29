(ns layzee.paging)

(defn page
  ":fn-producer -- the source
   :fn-stop     -- how we tell we're finished
   :result      -- collects the results"
  ([fn-producer fn-stop result]
     (if (apply fn-stop [result])
       result
         (recur fn-producer fn-stop (concat (apply fn-producer []) result)))))