(ns my-blog.core
  (:gen-class)
  (:use clj-time.format
        ring.adapter.jetty
        compojure.core
        compojure.handler
        net.cgrand.enlive-html
        my-blog.posts
        my-blog.users
        ring.util.response)
  (:require [cemerick.friend :as friend]
            (cemerick.friend [workflows   :as workflows]
                             [credentials :as creds]))) 
(declare app)
(defn start []
  (run-jetty #'app {:port 8080 :join? false}))
(defn -main []
  (run-jetty app
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
(deftemplate login-page "my_blog/templates/login.html" [])
(defroutes app*
  (GET "/admin/create" []
       (friend/authorize #{:admin}
        (create-form "" "")))
  (POST "/admin/create" [title content]
        (friend/authorize #{:admin}
         (insert-post title content)
         (redirect-after-post (str "/post/" title))))
  (GET "/post/:title" [title]
       (list-posts [(post title)]))
  (GET "/login" []
       (login-page))
  (GET "/" []
       (list-posts (posts))))
(def app
  (-> app*
      (friend/authenticate
       {:login-uri "/login"
        :unauthorized-redirect-uri "/login"
        :credential-fn (partial creds/bcrypt-credential-fn users)
        :workflows [(workflows/interactive-form)]})
      site))