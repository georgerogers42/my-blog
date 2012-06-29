(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor])
  (:require [clojure.string :as string]))
(defn markdown [mdown]
  (. (MarkdownProcessor.) markdown mdown))
(defn read-lines []
  (lazy-seq
   (when-let [line (read-line)]
     (cons line (read-lines)))))
(defn extract-metadata [html]
  (try 
    (with-in-str (slurp html)
      (let [meta (read)]
        (assoc meta
          :content (markdown
                    (apply str (interpose "\n" (read-lines)))))))
    (catch Exception e nil)))
(defn posts [& {:keys [dir] :or {dir "post"}}]
  (->> (.listFiles (java.io.File. dir))
       (map extract-metadata)
       (filter identity)
       (sort-by :updated)))
(defn post [posts]
  (into {} (map (juxt :title identity) posts)))
