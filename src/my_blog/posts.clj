(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor]))
(defn markdown [file]
  (.markdown (MarkdownProcessor.) (slurp file)))
(defn atomize-post [post]
  (assoc post
    :id  (str "georgerogers42.heroku.com/post/" (:title post))
    :url (str "/post/" (:title post))))
(def posts
  [{:title "Adding atom feeds in clojure"
    :updated "11-6-11"
   {:title  "Clojure programming"
    :updated "11-6-10"
    :content (markdown "hello.md")}])
(def post (into {} (map (juxt :title identity) posts)))
