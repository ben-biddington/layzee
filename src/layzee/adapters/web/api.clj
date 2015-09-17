(ns layzee.adapters.web.api
  (:require [clojure.data.json :as json]
            [layzee.adapters.twitter.search :refer :all :as twitter]
            [layzee.adapters.settings :as settings]
            [layzee.use-cases.lazy-web :as lazy-web]
            [layzee.adapters.twitter.nice-twitter :as nice-api]
            [layzee.adapters.twitter.conversation :as conversation]
            [layzee.adapters.amazon.dynamo-db :as db]
            [layzee.adapters.log :as log]
            [layzee.timing :as timing]))

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

(def ^{:private true} database-name settings/dynamo-db-tweet-table-name)

(def ^{:private true} conversation-search
     #(conversation/for (nice-api/get-tweet settings/amazon-credential database-name settings/oauth-credential) (:id_str %)))

(def init-database
     (memoize (fn[] (db/new-table settings/amazon-credential database-name))))

(defn- search[oauth-credential amazon-credential]
  (apply init-database [])
  (timing/time
   #(lazy-web/run { :search-adapter-fn lazy-web-search :conversation-adapter-fn conversation-search :log-fn log/info } {:count 10})
   #(log/info "It took <%sms> to run the use case" (:duration %))))

(defn- stack-trace[e]
  (clojure.string/join "\n" (map (fn[e] (.toString e)) (.getStackTrace e))))

(defn- reply-core[oauth-credential amazon-credential]
  (try
   (let [reply (search oauth-credential amazon-credential)]
     (ok { "X-Timestamp" (str (:timestamp reply))} (:result reply)))
   (catch Exception e
     (binding [*out* *err*]
       (clojure.stacktrace/print-stack-trace e))
     
     (err (str "[layzee.adapters.web.api] An error occured during `reply-core`: " (.getMessage e) (stack-trace e))))))

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

