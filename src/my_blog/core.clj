(ns my-blog.core
  (:gen-class)
  (:use clj-time.format
        ring.adapter.jetty
        compojure.core
        compojure.handler
        net.cgrand.enlive-html
        my-blog.posts
        ring.util.response)
  (:require [clj-time.format :as time-format]))
(declare app)
(defn start []
  (run-jetty (site #'app) {:port 8080 :join? false}))
(defn -main []
  (run-jetty (site app)
             {:port (Integer/parseInt (get (System/getenv) "PORT" "8080"))}))
(deftemplate list-posts "my_blog/templates/posts.html" [posts]
  [:article]
  (clone-for [post posts]
    [:a.title]
    (do->         
     (content (:title post))
     (set-attr :href (str "/post/" (:title post))))
    [:p.date]
    (content (str (:updated post)))
    [:.content]
    (html-content (:content post))))
(deftemplate create-form "my_blog/templates/create.html" [title body]
  [:#title]
  (set-attr :value title)
  [:#content]
  (content body))
(defroutes app
  (GET "/admin/create" []
       (create-form "" ""))
  (POST "/admin/create" [title content]
        (insert-post title content)
        (redirect-after-post (str "/post/" title))) 
  (GET "/post/:title" [title]
       (list-posts [(post title)]))
  (GET "/" []
       (list-posts (posts))))