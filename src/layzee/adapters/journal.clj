(ns layzee.adapters.journal
  (:require [clojure.tools.logging :as l]))

(defn record [msg & args]
     (let [msg (apply format (.replace (str msg) "%" "%%") args)]
       (l/info msg)))