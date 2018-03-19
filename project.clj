(defproject alton-towers "0.1.5-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
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
  :profiles {:uberjar {:aot :all}})
