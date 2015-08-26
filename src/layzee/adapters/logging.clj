(ns layzee.adapters.logging
  (:require [layzee.adapters.settings :as settings]))

(def log ^{:private true}
     (fn [msg & args]
       (when settings/log?
         (println (str "[log] " (apply format (.replace (str msg) "%" "%%") args))))))