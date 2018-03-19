(defproject alton-towers "0.1.6"
  :description "Scrapes the alton towers queue times for all the rides and stores them in a database."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.8.0"]
                 [gyptis "0.2.2"]
                 [clj-time "0.14.0"]
                 [com.layerware/hugsql "0.4.7"]
                 [mysql/mysql-connector-java "5.1.44"]
                 [environ "1.1.0"]]
  :main alton-towers.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :uberjar-name "alton-towers.jar"}})
