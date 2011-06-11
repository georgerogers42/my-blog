(ns my-blog.core
  (:use ring.adapter.jetty
        compojure.core
        hiccup.core
        hiccup.page-helpers
        my-blog.posts
        ring.middleware.stacktrace
        ring.middleware.params
        atompub.atom)
  (:require [clj-time.format :as time-format]))
(declare app)
(defn -main []
  (run-jetty (wrap-params app)
             {:port (Integer/parseInt (get (System/getenv) "PORT" "8080"))}))
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
      [:h2 [:a {:href "/"} "Back"]]]]))
(defroutes app
  (GET "/post/:title" [title]
       (show-post (post title)))
  (GET "/" []
       (list-all-posts))
  (GET "/atom.xml" []
       (atom-feed {:title "georgerogers42"
                   :url "http://georgerogers42.heroku.com/atom.xml"
                   :home-url "georgerogers42.herokuapp.com/"
                   :author-name "George Rogers"}
                  (map (comp atom-entry atomize-post) posts))))