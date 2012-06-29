(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor])
  (:require [clojure.string :as string]))
(defn markdown [mdown]
  (. (MarkdownProcessor.) markdown mdown))
(defn extract-metadata [html]
  (let [[meta & body] (string/split (slurp html) #"\n\n")]
    (assoc (read-string meta) :content (markdown (apply str body)))))
(def posts
  (sort-by :updated
           (map extract-metadata (.. (java.io.File. "post") listFiles))))
(def post
  (into {} (map (juxt :title identity) posts)))