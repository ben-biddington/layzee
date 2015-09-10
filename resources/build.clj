(require 'cljs.build.api)

(println "Compiling...")

(def output-dir "./public/js")

(cljs.build.api/build "src" {:main 'ui.bizz :output-dir output-dir :verbose true})

(println (format "Done -> %s" output-dir))