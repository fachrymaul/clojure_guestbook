(ns guessbook.models.db
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

(def guessbook-table-ddl
 (sql/create-table-ddl :guessbook
     [[:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
      [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
      [:name "TEXT"]
      [:message "TEXT"]]))

(defn create-guessbook-table []
  (sql/db-do-commands db-spec
   [guessbook-table-ddl
    "CREATE INDEX timestamp_index ON guessbook (timestamp);"]))

(defn read-guess []
  (sql/query db-spec ["SELECT * FROM guessbook ORDER BY timestamp DESC"]))

(defn save-message [name message]
  (sql/insert! db-spec
   :guessbook
   {:name name, :message message, :timestamp (new java.util.Date)}))
