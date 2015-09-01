(ns layzee.adapters.logging
  (:require [layzee.adapters.settings :as settings]
            [clojure.tools.logging :as l]))

(def log ^{:private true}
     (fn [msg & args]
       (when settings/log?
         (let [msg (apply format (.replace (str msg) "%" "%%") args)]
           (do
             (l/info msg) ;; http://www.bahmanm.com/blogs/how-to-add-logging-to-a-clojure-project
             (println (str "[log] " msg)))))))