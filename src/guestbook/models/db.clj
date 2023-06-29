(ns guestbook.models.db
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))


(def db {:classname   "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname     "db.sq3"})

(def db-spec
  {:dbtype "sqlite"
   :dbname "mydb"
   :user "myaccount"
   :password "secret"
   })

(def guestbook-table-ddl
 (sql/create-table-ddl :guestbook
     [[:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
      [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
      [:name "TEXT"]
      [:message "TEXT"]]))

(defn create-guestbook-table []
  (sql/db-do-commands db-spec
   [guestbook-table-ddl
    "CREATE INDEX timestamp_index ON guestbook (timestamp);"]))

(defn read-guess []
  (sql/query db-spec ["SELECT * FROM guestbook ORDER BY timestamp DESC"]))

(defn save-message [name message]
  (sql/insert! db-spec
   :guestbook
   {:name name, :message message, :timestamp (new java.util.Date)}))
