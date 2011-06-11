(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor]))
(defn markdown [file]
  (.markdown (MarkdownProcessor.) (slurp file)))
(defn atomize-post [post]
  (assoc post
    :id  (str "georgerogers42.heroku.com/post/" (:title post))
    :url (str "georgerogers42.heroku.com/post/" (:title post))))
(def posts
  [{:title  "Clojure programming"
    :updated "6-10-11"
    :content (markdown "hello.md")}])
(def post (into {} (map (juxt :title identity) posts)))
