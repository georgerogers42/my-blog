(ns my-blog.core
  (:use ring.adapter.jetty
        compojure.core
        hiccup.core
        hiccup.page-helpers
        my-blog.posts
        ring.middleware.stacktrace
        ring.middleware.params)
  (:require [clj-time.format :as time-format])
  (:gen-class))
(declare app)
(def passwords #{"atbbst15"})
(def users     #{"george"})
(defn -main []
  (run-jetty (wrap-params app)
             {:port (Integer/parseInt (get (System/getenv) "PORT" "8080"}))
(defn list-all-posts []
  (html5
    [:html
     [:head]
     [:body
      (for [post (reverse (sort-by :date posts))]
        [:div 
         [:h1 [:a {:href (str "/post/" (:title post))} (:title post)]]
         [:p [:em (:posted post)]]
         (:body post)])]]))
(defn show-post [post]
  (html5
    [:html
     [:head]
     [:body
      [:div 
       [:h1 (:title post)]
       [:p [:em (:posted post)]]
       [:p (:body post)]]
      [:h2 [:a {:href "/"} "Back"]]]]))
(defn post-form []
  (html5
   [:html
    [:head]
    [:body
     [:form {:method "post" :action "/posts/new"}
      "User:" [:input {:name :user}][:br]
      "Password: "[:input {:type :password :name :password}][:p]
      "Title: "[:input {:name :title}][:br]
      [:textarea {:name :body :rows 10 :cols 80}][:br]
      [:input {:type "submit"}]]]]))
(defroutes app
  (GET "/post/:title" [title]
       (show-post (first (post title))))
  (GET "/" []
       (list-all-posts)))