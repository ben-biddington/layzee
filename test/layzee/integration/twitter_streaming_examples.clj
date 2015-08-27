(ns layzee.integration.twitter-streaming-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer :all]
            [layzee.adapters.twitter.firehose :refer :all :as firehose]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.logging :refer :all]))

(facts
 (fact :wip "Can connect"
       (let [result (firehose/connect settings/oauth-credential)]
         (println result))))

(facts
 (fact "Can query a tweet"
       (let [result (firehose/get-tweet settings/oauth-credential "636438888567824384")]
         (println (json/write-str result :value-fn (fn[k v] (str v))))))) ;; [!] https://groups.google.com/forum/#!topic/clojure-liberator/Vcsy1Pp_jMo

