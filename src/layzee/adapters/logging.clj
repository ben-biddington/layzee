(ns layzee.adapters.logging
  (:require [layzee.adapters.settings :as settings]))

(def log ^{:private true}
     (fn [msg & args]
       (when settings/log?
         (let [msg (apply format (.replace (str msg) "%" "%%") args)]
           (do
             (println (str "[log] " msg)))))))