(ns alton-towers.core
  (:require [alton-towers.to-db :refer [root-dir children process-files start-process-threads
                                        json->map]]
            [clojure.core.async :refer [>! go]]
            [environ.core :refer [env]])
  (:gen-class))

(def base-url (or (env :base-url)
                  "http://ridetimes.co.uk/queue-times-new.php?r="))

(defn get-url
  []
  (str base-url (rand) 8))

(def in-chan (atom (start-process-threads (or (env :threads)
                                              1))))

(defn process
  []
  (go
    (let [data (slurp (get-url))]
      (>! @in-chan data))))

(defn -main
  [& args]
  (while true
    (do
      (process)
      (Thread/sleep (* 10 1000)))))
