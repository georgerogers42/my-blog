(ns my-blog.posts
  (:require [clojureql.core :as cql])
  (:require [clj-time.core :as time]
            [clj-time.format :as time-format]))
(def db
  {:classname "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "my-blog.db"})
(def posts
  (cql/table db :posts))
(defn add-post [post]
  (cql/conj! posts (assoc post :posted (time/now))))
(defn post [title]
  (cql/select posts (cql/where (= :title title))))