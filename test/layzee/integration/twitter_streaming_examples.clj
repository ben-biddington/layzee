(ns layzee.integration.twitter-streaming-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]
            [layzee.adapters.settings :refer :all :as settings]
            ))

(facts
 (fact :wip "Can connect"
       (let [result (twitter/stream-connect settings/oauth-credential)]
         (println result))))

