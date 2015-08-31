(ns layzee.adapters.oauth
  (:require 
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :refer :all]
            [bone.auth-header :as auth]
            [bone.signature :as signature]
            [bone.signature-base-string :as base-string]
            [bone.timestamps :as ts]))

(defn sign[url verb parameters oauth-credential]
  (let [opts {:verb verb :url url :parameters parameters :timestamp-fn ts/next :nonce-fn ts/next :log #(println %)}]
    (auth/sign oauth-credential opts)))