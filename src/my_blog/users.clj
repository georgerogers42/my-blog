(ns my-blog.users
  (:use cemerick.friend.credentials)
  (:require [clojure.java.jdbc :as sql]))

(def db (System/getenv "DATABASE_URL"))
(defn users [username]
  (sql/with-connection db
    (sql/with-query-results rows
      ["SELECT username, password FROM users WHERE username = ? LIMIT 1" username]
      (assoc (first rows) :roles #{:admin}))))

(defn create-user [username password]
  (let [password (hash-bcrypt password)]
    (sql/with-connection db
      (sql/do-prepared "INSERT INTO users(username, password) VALUES (?, ?)"
                       [username password]))))