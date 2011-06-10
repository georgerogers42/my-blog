(defproject my-blog "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [hiccup "0.3.5"]
                 [compojure "0.6.3"]
                 [ring-reload-modified "0.1.0"]
                 [ring/ring-devel "0.3.8"]
                 [ring/ring-core  "0.3.8"]
                 [ring/ring-jetty-adapter "0.3.8"]
                 [clj-time "0.3.0"]]
  :dev-dependencies [[swank-clojure "1.3.1"]]
  :main my-blog.core)

