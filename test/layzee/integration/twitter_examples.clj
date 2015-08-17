(ns layzee.integration.twitter-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [layzee.adapters.twitter :refer :all :as twitter]))

(facts
 (fact "Can read the timeline"
       (let [result (twitter/lazy-web)]
         (< 0 (count result)) => true)))

;;(facts
;; (fact "Each tweet contains the tag #lazyweb"
;;       (let [result (first (twitter/lazy-web))]
;;         (.contains "lazyweb" (:tags result)) => true)))

(facts
 (fact "Signing works like this"
       ;; the example from -> https://dev.twitter.com/oauth/application-only
       (let [result (twitter/sign {:key "xvz1evFS4wEEPTGEFPHBog" :secret "L8qq9PZyRg6ieKGEKhZolGC0vJWLw8iEJ88DRdyOg"})]
         result => "eHZ6MWV2RlM0d0VFUFRHRUZQSEJvZzpMOHFxOVBaeVJnNmllS0dFS2hab2xHQzB2SldMdzhpRUo4OERSZHlPZw==")))