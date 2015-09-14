(ns layzee.adapters.web.api
  (:require [clojure.data.json :as json]
            [layzee.adapters.twitter.search :refer :all :as twitter]
            [layzee.adapters.settings :as settings]
            [layzee.use-cases.lazy-web :as lazy-web]
            [layzee.adapters.twitter.nice-twitter :as nice-api]
            [layzee.adapters.twitter.conversation :as conversation]
            [layzee.adapters.amazon.simple-db :as simple-db]))

(def content-type-plain-text {"Content-Type" "text/plain"})
(def content-type-json {"Content-Type" "application/json"})

(defn- ok
  ([body] (ok content-type-json body))
  ([headers body] {
                 :status  200
                 :headers (merge headers content-type-json)
                 :body    (json/write-str body)}))

(defn- err[body] {
                 :status  500
                 :headers content-type-json
                 :body    (json/write-str body)})

(def ^{:private true} lazy-web-search
  (fn [opts]
    (twitter/lazy-web settings/twitter-bearer-token opts)))

(def ^{:private true} conversation-search
     #(conversation/for (nice-api/get-tweet settings/amazon-credential "layzee-web" settings/oauth-credential) %))

(def ^{:private true} database-name "layzee-web")     

(def init-database (memoize #(simple-db/create-domain settings/amazon-credential database-name)))

;; @todo: move in to use-case
(defn- assoc-replies-for[oauth-credential amazon-credential tweet]
  (apply init-database []) 
  (assoc tweet :replies (apply conversation-search [(-> tweet :id_str)])))

(defn- search[oauth-credential amazon-credential]
  (let [results (lazy-web/run { :search-adapter-fn lazy-web-search } {:count 10} )]
    (let [results-with-replies (pmap (partial assoc-replies-for oauth-credential amazon-credential) (:result results))]
      (assoc results :result results-with-replies))))

(defn- reply-core[oauth-credential amazon-credential]
  (try
   (let [reply (search oauth-credential amazon-credential)]
     (ok { "X-Timestamp" (str (:timestamp reply))} (:result reply)))
   (catch Exception e (err (str "caught exception: " (.getMessage e))))))

(defn reply
  ;; (
  ;;   {
  ;;     :ssl-client-cert nil, :cookies {}, :remote-addr 127.0.0.1,
  ;;     :params {:* /api},
  ;;     :flash nil,
  ;;     :route-params {:* /api},
  ;;     :headers {user-agent curl/7.38.0, accept */*, host localhost:5000},
  ;;     :server-port 5000, :content-length nil, :form-params {}, :session/key nil,
  ;;     :query-params {}, :content-type nil, :character-encoding nil,
  ;;     :uri /api, :server-name localhost,
  ;;     :query-string nil,
  ;;     :body #<HttpInput org.eclipse.jetty.server.HttpInput@35227d5c>, :multipart-params {}, :scheme :http,
  ;;     :request-method :get, :session {}
  ;;    }
  ;; )
  ([request]
     (reply request {}))
  ([request opts]
     (reply-core settings/oauth-credential settings/amazon-credential)))

