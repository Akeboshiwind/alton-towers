(ns alton-towers.to-db
  (:require [cheshire.core :refer :all]
            [clojure.java.io :refer [file]]
            [clojure.string :refer [last-index-of]]
            [clojure.core.async :as a]
            [clj-time.core :refer [now]]
            [clj-time.coerce :refer [from-long]]
            [clj-time.format :as f]
            [alton-towers.db :as db]
            [clojure.java.jdbc :as jdbc]))

(def root-dir (file "."))

(defn children
  [root]
  (file-seq root))

(defn extension
  [file]
  (let [name (.getName file)
        ext-index (last-index-of name ".")]
    (when (not (nil? ext-index))
      (-> name
          (subs ext-index)
          (.toLowerCase)))))

(defn json-file?
  [^java.io.File f]
  (= (extension f)
     ".json"))

(defn ->keyword
  [s]
  (-> s
      (.toLowerCase)
      (clojure.string/replace #"_" "-")
      (keyword)))

(defn parse-int
  [s]
  (Integer. (re-find #"\d+" s)))

(def output-formatter (f/formatter "YYYY-MM-dd HH:mm:ss"))

(defn file-name->date-str
  [name]
  (let [dash-idx (last-index-of name "-")
        ext-idx (last-index-of name ".")
        date-str (subs name (+ 1 dash-idx) ext-idx)
        date (from-long (* 1000 (parse-int date-str)))
        new-date-str (f/unparse output-formatter date)]
    new-date-str))

(defn json->map
  [j date-str]
  (try
    (-> j
        (parse-string ->keyword)
        (->> (map #(assoc % :date date-str))))
    (catch com.fasterxml.jackson.core.JsonParseException e
      (do
        (println "You call that JSON?")
        (println "This is JSON:
<message>
    <to>You<to/>
    <text>That's not JSON...<text/>
<message/>")
        nil))))

(defn file->map
  [^java.io.File f]
  (let [name (.getName f)
        new-date-str (file-name->date-str name)]
    (-> (slurp f)
        (json->map new-date-str))))

(defn process-file
  [f]
  (when (json-file? f)
    (file->map f)))

(defn start-process-threads
  "Returns a channel that accepts alton-towers json files.
  Starts `n` threads processing files put onto the returned channel.
  Starts a thread putting the processed data into a database."
  [n]
  (let [in-chan (a/chan)
        out-chan (a/chan)
        c (atom 0)]
    (dotimes [_ n]
      (a/go
        (println "Starting a processing thread")
        (while true
          (let [in (a/<! in-chan)
                date-str (f/unparse output-formatter (now))
                data (json->map in date-str)]
            (when-not (nil? data)
              (a/>! out-chan data))))))
    (a/go
      (println "Starting ->SQL loop")
      (while true
        (let [rides (a/<!! out-chan)]
          (swap! c inc)
          (println (case (mod @c 4)
                     0 "Eat"
                     1 "Sleep"
                     2 "Process"
                     3 "Repeat"))
          (jdbc/with-db-transaction [tx db/db]
            (doseq [r rides]
              r)))))
    in-chan))

(defn process-files
  [files threads]
  (let [in-chan (start-process-threads threads)]
    (a/go
      (do
        (println "Starting adding files to channel")
        (doseq [f files]
          (a/>! in-chan f))
        (println "Done adding files to channel")))))
