(defproject my-blog "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [enlive "1.0.1"]
                 [compojure "1.1.0"]
                 [ring "1.1.1"]
                 [org.markdownj/markdownj "0.3.0-1.0.2b4"]]
  :plugins [[lein-swank "1.4.4"]]
  :main my-blog.core)

