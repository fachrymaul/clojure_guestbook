(ns guestbook.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [guestbook.routes.home :refer [home-routes]]
            [guestbook.models.db :as db]
            [guestbook.routes.auth :refer [auth-routes]]
            [noir.session :as session]
            [ring.middleware.session.memory :refer [memory-store]]))

(defn init []
  (println "guestbook is starting")
  (if-not (.exists (java.io.File. "./mydb"))
    (db/create-guestbook-table)
    (println "db ready")))

(defn destroy []
  (println "guestbook is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes
       auth-routes
       home-routes
       app-routes)
      (handler/site)
      (wrap-base-url)
      (session/wrap-noir-session {:store (memory-store)})))
