(defproject layzee "0.1.0-SNAPSHOT"
  :description "..."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "2.0.0"]
                 [clj-time "0.11.0"]
                 [org.clojure/data.json "0.2.6"]
                 [bone "0.10.0-SNAPSHOT"]
                 [http.async.client "0.6.1"]
                 [http-kit "2.1.18"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/tools.logging "0.2.4"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]
                 [compojure "1.1.8"]
                 [ring/ring-jetty-adapter "1.2.2"]
                 [environ "0.5.0"]
                 [org.clojure/core.memoize "0.5.6"]
		 [org.clojure/clojurescript "1.7.48"]
                 [com.cemerick/rummage "1.0.1"]
                 [listora/again "0.1.0"]]
  :main ^:skip-aot layzee.core
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[midje "1.7.0" :exclusions [org.clojure/clojure]]]
                    :plugins [[lein-midje "3.1.3"]]}})

