(ns my-blog.posts
  (:import [com.petebevin.markdown MarkdownProcessor])
  (:require [clojure.string :as string]
            [clojure.java.jdbc :as sql]
            [my-blog.users :as users]))
(def db (System/getenv "DATABASE_URL"))
(defn markdown [mdown]
  (. (MarkdownProcessor.) markdown mdown))
(defn posts []
  (sql/with-connection db
    (sql/with-query-results rows
      ["SELECT title, updated, content FROM posts ORDER BY updated DESC"]
      (doseq [row rows])
      rows)))
(defn insert-post [title content]
  (sql/with-connection db
    (sql/update-or-insert-values
     :posts
     ["title=?" title]
     {:title title
      :content content
      :updated (java.sql.Timestamp. (System/currentTimeMillis))})))
(defn create-db []
  (sql/with-connection db
    (sql/do-commands
     "CREATE TABLE posts(title char(80) primary key,
                         updated timestamp not null,
                         content text not null)"
     "CREATE TABLE users(username char(80) primary key,
                         password varchar(255) not null)")
    (let [_ (println "new username:")
          username (read-line)
          _ (println "new password:")
          password (read-line)]
      (users/create-user username password))))
(defn post [title]
  (sql/with-connection db
    (sql/with-query-results rows
      ["SELECT title, updated, content FROM posts WHERE title = ? LIMIT 1" title]
      (first rows))))
