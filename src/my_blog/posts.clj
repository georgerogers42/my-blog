(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor]))
(defn markdown [file]
  (.markdown (MarkdownProcessor.) (slurp file)))
(defn atomize-post [post]
  (assoc post
    :id  (str "georgerogers42.heroku.com/post/" (:title post))
    :url (str "/post/" (:title post))))
(def posts
  [{:title "On the balance of Quick and Dirty and Clean Code"
    :update "2011-6-11"
    :content (markdown "posts/qnd.md")}]
   {:title "Adding atom feeds in clojure"
    :updated "2011-6-11"
    :content (markdown "posts/atom.md")}
   {:title  "Clojure programming"
    :updated "2011-6-10"
    :content (markdown "posts/hello.md")}])
(def post (into {} (map (juxt :title identity) posts)))
