(defproject layzee "0.1.0-SNAPSHOT"
  :description "..."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot layzee.core
  :target-path "target/%s"
  :profiles {:dev {:dependencies [[midje "1.7.0" :exclusions [org.clojure/clojure]]]
                    :plugins [[lein-midje "3.1.3"]]}})

