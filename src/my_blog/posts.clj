(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor])
  (:require [clojure.string :as string]
            [clojure.java.jdbc :as sql])
  (:use clj-time.format))
(def db (System/getenv "DATABASE_URL"))
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
#_(defn posts [& {:keys [dir] :or {dir "post"}}]
  (->> (.listFiles (java.io.File. dir))
       (map extract-metadata)
       (filter identity)
       (map #(update-in % [:updated] (partial parse (formatters :date-hour))))
       (sort-by :updated)))
(defn posts []
  (sql/with-connection db
    (sql/with-query-results rows
      ["SELECT title, updated, content FROM posts ORDER BY updated DESC"]
      (doseq [row rows])
      rows)))
(defn insert-post [title content]
  (sql/with-connection db
    (sql/do-prepared
     "INSERT INTO posts(title, updated, content) VALUES (?, now(), ?)"
     [title content])))
(defn create-db []
  (sql/with-connection db
    (sql/do-commands
     "CREATE TABLE posts(title char(80) primary key,
                         updated timestamp not null,
                         content text not null)")))
(defn post [title]
  (sql/with-connection db
    (sql/with-query-results rows
      ["SELECT title, updated, content FROM posts WHERE title = ? LIMIT 1" title]
      (first rows))))