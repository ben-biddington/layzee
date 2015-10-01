(ns layzee.paging)

;(defn page
;  ":fn-producer -- the source
;   :fn-stop     -- how we tell we're finished
;   :result      -- collects the results"
;  ([fn-producer fn-stop result]
;     (if (apply fn-stop [result])
;       result
;       (recur fn-producer fn-stop (concat (apply fn-producer []) result)))))

(defn- default-fn-args[result args] args)

(defn- produce[fn args]
  (apply fn
         (if (nil? args)
           []
           [args])))

(defn page
  ":fn-producer -- function that produces the results
   :fn-args     -- function that produces arguments for the producer
   :fn-stop     -- how we tell we're finished
   :result      -- collects the results
   :args        -- [optional] arguments for :fn-producer. Defaults to nil(, and :fn-producer is invoked with zero arity)."
  ([fn-producer fn-stop result]
     (page fn-producer default-fn-args fn-stop result nil))
  ([fn-producer fn-args fn-stop result args]
     (if (apply fn-stop [result])
       result
       (recur fn-producer fn-args fn-stop (concat (produce fn-producer args) result) (apply fn-args [result args])))))