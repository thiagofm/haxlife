(ns haxlife.data.db
  (:require [datascript.core :as d]))

(def conn (d/create-conn {}))

(defn lambdas-setup [per-second per-character]
  (d/transact! conn
               [{:db/id -1
                 :lambdas/total 0
                 :lambdas/per-second per-second
                 :lambdas/per-character per-character}]))

; Starting state
(d/transact! conn
             [{:db/id -1
               :game/active-tab-component "Store"
               :game/tutorial true}])
