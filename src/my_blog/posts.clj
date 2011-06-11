(ns my-blog.posts
  (:import [com.petebevin.markdownj MarkdownProcessor]))
(defn markdown [file]
  (.process (MarkdownProcessor. file))) 
(def posts
  [{:title  "Hello World"
    :posted "Friday June 10, 2011"
    :body (markdown "hello.md")}])
(def post (into {} (map (juxt :title identity) posts)))
