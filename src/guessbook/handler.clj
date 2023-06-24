(ns guessbook.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [guessbook.routes.home :refer [home-routes]]
            [guessbook.models.db :as db]
            [guessbook.routes.auth :refer [auth-routes]]))

(defn init []
  (println "guessbook is starting")
  (if-not (.exists (java.io.File. "./mydb"))
    (db/create-guessbook-table)
    (println "db ready")))

(defn destroy []
  (println "guessbook is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes auth-routes home-routes app-routes)
      (handler/site)
      (wrap-base-url)))
