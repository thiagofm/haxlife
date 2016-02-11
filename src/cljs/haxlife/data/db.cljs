(ns haxlife.data.db
  (:require [datascript.core :as d]))

(def conn (d/create-conn {}))

(def x 1)

; Starting state
(d/transact! conn
             [{:db/id -1
               :game/tutorial true}])

