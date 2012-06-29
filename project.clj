(defproject my-blog "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.xml "0.0.3"]
                 [enlive "1.0.1"]
                 [hiccup "1.0.0"]
                 [compojure "1.1.0"]
                 [ring "1.1.1"]
                 [clj-time "0.3.0"]
                 [org.markdownj/markdownj "0.3.0-1.0.2b4"]]
  :dev-dependencies [[swank-clojure "1.4.0"]]
  :main my-blog.core)