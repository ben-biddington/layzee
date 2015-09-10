(require 'cljs.build.api)

(defn- say [what & args] (println (format "[CLJS] %s" (apply format what args))))

(def output-dir "./public/js")
(def opts {:main 'ui.bizz :output-dir output-dir :verbose true})

(say "Compiling javascript from src/ to %s" output-dir)

(cljs.build.api/build "src" opts)

(say "Done." output-dir)