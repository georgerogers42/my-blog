(ns my-blog.core
  (:use ring.adapter.jetty
        compojure.core
        hiccup.core
        hiccup.html-helpers))
(defn -main []
  (run-jetty app {:port 8080}))

