(defproject my-blog "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [postgresql/postgresql "9.1-901.jdbc4"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [clj-time "0.4.3"]
                 [enlive "1.0.1"]
                 [compojure "1.1.0"]
                 [ring "1.1.1"]
                 [org.markdownj/markdownj "0.3.0-1.0.2b4"]]
  :plugins [[lein-swank "1.4.4"]]
  :main my-blog.core)

