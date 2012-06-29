(ns my-blog.core
  (:gen-class)
  (:use ring.adapter.jetty
        compojure.core
        net.cgrand.enlive-html
        my-blog.posts
        ring.middleware.stacktrace
        ring.middleware.params)
  (:require [clj-time.format :as time-format]))
(declare app)
(defn -main []
  (run-jetty (wrap-params app)
             {:port (Integer/parseInt (get (System/getenv) "PORT" "8080"))}))
(deftemplate list-posts "my_blog/templates/posts.html" [posts]
  [:article]
  (clone-for [post posts]
    [:a.title]
    (do->         
     (content (:title post))
     (set-attr :href (str "/post/" (:title post))))
    [:p.date]
    (content (:updated post))
    [:.content]
    (html-content (:content post))))
(defroutes app
  (GET "/post/:title" [title]
       (list-posts [(post title)]))
  (GET "/" []
       (list-posts posts)))
