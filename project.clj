(defproject guessbook "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [ring-server "0.5.0"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.xerial/sqlite-jdbc "3.36.0.3"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler guessbook.handler/app
         :init guessbook.handler/init
         :destroy guessbook.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring/ring-mock "0.4.0"] [ring/ring-devel "1.7.1"]]}})
