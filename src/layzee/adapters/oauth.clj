(ns layzee.adapters.oauth
  (:require 
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :refer :all]
            [bone.auth-header :as auth]
            [bone.signature :as signature]
            [bone.timestamps :as ts]))

(defn sign[url parameters oauth-credential]
  (let [opts {:verb "GET" :url url :parameters parameters :timestamp-fn ts/next :nonce-fn ts/next}]
    (auth/sign oauth-credential opts)))