(ns layzee.adapters.twitter.search
  (:require [clj-http.client :as http]
            [clj-http.util :as util]
            [clojure.data.json :as json]
            [layzee.adapters.settings :as settings]
            [layzee.adapters.logging :as logging]
            [bone.signature-base-string :as signature-base-string]
            [bone.signature :as signature]
            [layzee.adapters.twitter.authentication.bearer-tokens :as bearer-tokens]
            [layzee.net :refer :all]
            [layzee.paging :as paging]))

;; https://github.com/dakrone/clj-http
;; https://apps.twitter.com/app/8673064

(defn- bearer-auth[token] {
   "Authorization" (str "Bearer " token)
   "Accept"        "application/json"})

(defn- search-since[bearer-token what log opts])

(defn- earl[what opts]
  (let [how-many (or (:count opts) 15) tweet-filter (or (:filter opts) (fn[what] what)) max-id (:max-id opts)]
    (format "https://api.twitter.com/1.1/search/tweets.json?result_type=recent&count=%s&q=%s&max_id=%s" how-many (% what) (% (str max-id)))))

(defn- search [bearer-token what log opts]
  "opts[:count]  -- The number of results to return
   opts[:max-id] -- The id of the newst tweet we have already received
   opts[:filter] -- The filter to apply to the results before returning. For example, you may wish to omit retweets.

   <https://dev.twitter.com/rest/public/timelines> Note that since the max_id parameter is inclusive, the Tweet with the matching ID will actually be returned again."
  (log opts)
  (println opts)
  (let [max-id (or (:max-id opts) 0) tweet-filter (or (:filter opts) (fn[what] what))]
    (let [url (earl what opts)]
      (log url)
      (let [reply (http/get url {:headers (bearer-auth bearer-token)})]
        (log (:body reply))
        (let [tweets (:statuses (json/read-str (:body reply) :key-fn keyword))]
          (filter tweet-filter tweets))))))

(defn by-keyword[bearer-token keyword & opts]
  (logging/log opts)
  (search bearer-token keyword logging/log (if (nil? opts) {} (first opts))))

(defn- oldest-id[list args]
  "https://dev.twitter.com/rest/public/timelines"
  (if (empty? list)
    (:max-id args)
    (apply min (map #(:id %) list))))

(defn by-keyword-paged[bearer-token keyword opts]
  (let [limit (or (:limit opts) 15) page-size (or (:page-size opts) 100) log (or (:log opts) logging/log)]
    (take limit (paging/page
     (partial search bearer-token keyword log)
     #(merge %2 {:max-id (- (oldest-id %1 %2) 1)})
     #(<= limit (count %))
     '()
     {:count page-size}))))

(defn lazy-web
  ([bearer-token] (lazy-web bearer-token {}))
  ([bearer-token opts]
     (by-keyword bearer-token "#lazyweb" opts)))

