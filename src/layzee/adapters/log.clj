(ns layzee.adapters.log
  (:require [clojure.tools.logging :as l]))

(defn info [msg & args] (l/info (apply format (.replace (str msg) "%" "%%") args)))