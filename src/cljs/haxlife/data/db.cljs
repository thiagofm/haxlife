(ns haxlife.data.db
  (:require [datascript.core :as d]))

(def conn (d/create-conn {}))

; Starting state
(d/transact! conn
             [{:db/id -1
               :app/title "Hello, DataScript!"
               :app/count 0}])
