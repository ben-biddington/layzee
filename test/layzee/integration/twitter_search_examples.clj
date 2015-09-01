(ns layzee.integration.twitter-search-examples
  (:use midje.sweet)
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json :refer [write-str]]
            [layzee.adapters.settings :refer :all :as settings]
            [layzee.adapters.twitter.api :as api]
            [layzee.adapters.logging :refer :all]))

(facts
 (fact "Can find a single tweet by its id"
       (let [result (api/get-tweet settings/oauth-credential "636438888567824384")]
         (:text result) => "\"Rachel Hunter's Tour of Beauty\" jonkeys_bumbreath#"))

 (fact "It returns nil when tweet doe snot exist"
       (let [result (api/get-tweet settings/oauth-credential "xxx-does-not-exist-xxx")]
         result => nil))) ;; [!] https://groups.google.com/forum/#!topic/clojure-liberator/Vcsy1Pp_jMo

;; TEST: returns nil when not found

