# Compiling

Based on [the quick start](https://github.com/clojure/clojurescript/wiki/Quick-Start).

Only I had to include cljs like this (in specific order)

```
    <script type="text/javascript" src="/js/goog/base.js"></script>
    <script type="text/javascript" src="/js/cljs/core.js"></script>
    <script type="text/javascript" src="/js/ui/bizz.js"></script>
```

Download `cljs.jar`:

```
wget https://github.com/clojure/clojurescript/releases/download/r1.7.48/cljs.jar -O cljs.jar

```

build:

```
java -cp cljs.jar:src clojure.main build.clj
```

Build config is in `build.clj`.