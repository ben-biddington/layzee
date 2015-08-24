(ns layzee.integration.twitter-streaming-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]
            [bone.signature-base-string :refer :all :as signature_base_string]))

(facts
 (fact :wip "Can connect"
       (let [result (twitter/stream-connect settings/consumer-token)]
         (println result))))

(facts
 (fact "can make a signature base string"))

