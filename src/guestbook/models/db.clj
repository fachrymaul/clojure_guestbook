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

(def user-table-ddl
  (sql/create-table-ddl :users
                        [[:id "varchar(20) PRIMARY KEY"]
                         [:pass "varchar(100)"]]))

(defn create-user-table []
  (sql/db-do-commands db-spec
                      [user-table-ddl
                       "CREATE INDEX id_index ON users (id);"]))

(defn add-user-record [user]
  (sql/insert! db-spec
               :users
               {:id (:id user), :pass (:pass user)}))

(defn get-user [id]
  (first (sql/query db-spec ["SELECT * FROM users WHERE ID = ?" id])))

(defn get-all-user []
  (sql/query db-spec ["SELECT * FROM users"]))
