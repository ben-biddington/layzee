# Compiling

Based on [the quick start](https://github.com/clojure/clojurescript/wiki/Quick-Start).

Download `cljs.jar`:

```
wget https://github.com/clojure/clojurescript/releases/download/r1.7.48/cljs.jar -O cljs.jar
```

build:

```
java -cp cljs.jar:src clojure.main build.clj
```

Build config is in `build.clj`.

Add to `index.html`. I had to include `/js/cljs/core.js` like this (in specific order)

```
    <script type="text/javascript" src="/js/goog/base.js"></script>
    <script type="text/javascript" src="/js/cljs/core.js"></script>
    <script type="text/javascript" src="/js/ui/bizz.js"></script>
```
otherwise I got errors like:

```
goog.require could not find: cljs.coregoog.logToConsole_ @ base.js:619goog.require @ base.js:660(anonymous function) @ bizz.cljs:1
base.js:662 Uncaught Error: goog.require could not find: cljs.core
```
