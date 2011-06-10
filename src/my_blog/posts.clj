(ns my-blog.posts)
(def posts
  [{:title  "Hello World"
    :posted "Friday June 10, 2011"
    :body "Hello World"}])
(def post (into {} (map (juxt :title identity) posts)))
