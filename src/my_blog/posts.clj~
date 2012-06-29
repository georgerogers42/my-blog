(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor])
  (:require [clojure.string :as string]))
(defn markdown [mdown]
  (. (MarkdownProcessor.) markdown mdown))
(defn extract-metadata [html]
  (let [html (slurp html)
        [meta & body] (string/split html #"\n\n")]
    (assoc (read-string html)
      :content (markdown
                (apply str
                       (interpose "\n\n" body))))))
(def posts
  (sort-by :updated
           (map extract-metadata (.. (java.io.File. "post") listFiles))))
(def post
  (into {} (map (juxt :title identity) posts)))