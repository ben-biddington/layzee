(ns layzee.integration.twitter-streaming-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter.firehose :refer :all :as firehose]
            [layzee.adapters.settings :refer :all :as settings]
            ))

(facts
 (fact :wip "Can connect"
       (let [result (firehose/connect settings/oauth-credential)]
         (println result))))

