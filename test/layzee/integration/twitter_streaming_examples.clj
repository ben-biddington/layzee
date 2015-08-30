(ns layzee.integration.twitter-streaming-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer [write-str]]
            [layzee.adapters.twitter.firehose :refer :all :as firehose]
            [layzee.adapters.twitter.api :refer :all :as api]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.logging :refer :all]))

(facts
 (fact :wip "Can connect"
       (let [result (firehose/connect settings/oauth-credential)]
         (println result)))

 (fact :wip "Can use the 'sample' endpoint" ;; can use the 'sample' endpoint"
       (let [result (firehose/sample settings/oauth-credential)]
         (println result))))

 ;; [!] https://groups.google.com/forum/#!topic/clojure-liberator/Vcsy1Pp_jMo

