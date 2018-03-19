(ns alton-towers.db
  (:require [hugsql.core :as hugsql]
            [environ.core :refer [env]]))

(let [db-host (or (env :db-host) "192.168.0.10")
      db-port (or (env :db-port) 3306)
      db-name (or (env :db-name) "alton-towers")]
  (def db {:classname "com.mysql.jdbc.Driver" ; must be in classpath
           :subprotocol "mysql"
           :subname (str "//" db-host ":" db-port "/" db-name)
                                        ; Any additional keys are passed to the driver
                                        ; as driver-specific properties.
           :user (or (env :db-user) "admin")
           :password (env :db-password)}))

(hugsql/def-db-fns "alton_towers/rides.sql")
