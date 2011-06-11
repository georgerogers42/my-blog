(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor]))
(defn markdown [file]
  (.markdown (MarkdownProcessor.) (slurp file))) 
(def posts
  [{:title  "Hello World"
    :posted "Friday June 10, 2011"
    :body (markdown "hello.md")}])
(def post (into {} (map (juxt :title identity) posts)))
