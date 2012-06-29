(ns my-blog.core
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
    [:p.content]
    (html-content (:content post))))
  
(comment
  (defn list-all-posts []
    (html5
     [:html
     [:head
      [:link {:href "/atom.xml" :type "application/atom+xml"
              :rel "alternate"
              :title "Sitewide ATOM Feed"}]]
      [:body
       [:a {:href "/atom.xml"} "Atom Feed"]
       (for [post posts]
         [:div 
          [:h1 [:a {:href (str "/post/" (:title post))} (:title post)]]
         [:p [:em (:updated post)]]
          (:content post)])]]))
  (defn show-post [post]
    (html5
     [:html
      [:head]
      [:body
       [:div 
        [:h1 (:title post)]
        [:p [:em (:updated post)]]
        [:p (:content post)]]
       [:h2 [:a {:href "/"} "Back"]]]])))
(defroutes app
  (GET "/post/:title" [title]
       (list-posts [(post title)]))
  (GET "/" []
       (list-posts posts)))