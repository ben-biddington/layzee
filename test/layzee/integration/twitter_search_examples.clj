(ns layzee.integration.twitter-search-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer [write-str]]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.twitter.api :as api]
            [layzee.adapters.logging :refer :all]))

(facts
 (fact "Can find a single tweet bu its id"
       (let [result (api/get-tweet settings/oauth-credential "636438888567824384")]
         (println (json/write-str result :value-fn #(str %2)))))) ;; [!] https://groups.google.com/forum/#!topic/clojure-liberator/Vcsy1Pp_jMo

