(ns layzee.adapters.disk-settings
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]
            [clojure.data.json :as json]))

(defn called[name])