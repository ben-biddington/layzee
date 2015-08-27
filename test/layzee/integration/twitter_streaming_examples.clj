(ns layzee.integration.twitter-streaming-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter.firehose :refer :all :as firehose]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.logging :refer :all]))

(facts
 (fact :wip "Can connect"
       (log settings/oauth-credential)
       (let [result (firehose/connect settings/oauth-credential)]
         (println result))))

