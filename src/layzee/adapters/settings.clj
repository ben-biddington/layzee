(ns layzee.adapters.settings
  (:require [clojure.test :refer :all]
            [clojure.data.json :as json]
            [clojure.java.io :as io]
            [layzee.adapters.twitter.authentication.bearer-tokens :as bearer-tokens]))

(defn- fail[reason & args]
  ((throw (Exception. (apply format reason args)))))

(defn- env?[name] (not (nil? (System/getenv name))))

(defn exists? [filename] (.exists (io/as-file filename)))

(defn- from-disk [filename]
  (when (exists? filename)
    (json/read-str (slurp filename) :key-fn keyword)))

(defn- env[name] (System/getenv name))

(def log? (env? "LOG"))

(def oauth-credential
     (or
      (from-disk ".twitter")
      { :consumer-key (env "TWITTER_CONSUMER_KEY")  :consumer-secret (env "TWITTER_CONSUMER_SECRET") }
      (fail "You need to supply the <TWITTER_CONSUMER_KEY,TWITTER_CONSUMER_SECRET> environment variables, or the <%s> file." name ".twitter")))

(def amazon-credential
     (or
      (from-disk ".amazon")
      { :access-key-id (env "AMAZON_ACCESS_KEY_ID")  :secret-access-key (env "AMAZON_SECRET_ACCESS_KEY") }
      (fail "You need to either supply the <AMAZON_ACCESS_KEY_ID,AMAZON_SECRET_ACCESS_KEY> environment variables or the <%s> file. Find them at <https://console.aws.amazon.com/iam/home?region=us-west-2#security_credential>" ".amazon")))

(def twitter-bearer-token (bearer-tokens/bearer-token-for (:consumer-key oauth-credential) (:consumer-secret oauth-credential)))

(def dynamo-db-tweet-table-name (or (env "TWEET_TABLE_NAME") "layzee-web"))
