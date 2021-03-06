(ns layzee.adapters.web.server
  (:gen-class)
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as resp]
            [environ.core :refer [env]]
            [layzee.adapters.web.api :as api]
            [layzee.adapters.log :as log]
            [layzee.timing :as timing]))

(defroutes app
  (ANY "/api" request (timing/time #(api/reply request) #(log/info "It took <%sms> to reply to <%s>" (:duration %) request)))
  (GET "/"    [] (resp/resource-response "index.html" {:root "public"}))
  (route/resources "/")
  (ANY  "*"   [] (route/not-found (slurp (io/resource "404.html")))))
  

(defn- log[msg] (log/info msg))

(defn main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (println (format "Starting server on port %s" port))
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))