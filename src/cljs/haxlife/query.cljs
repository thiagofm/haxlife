(ns haxlife.query
  (:require [om.next :as om]))

(defmulti read om/dispatch)

(defmethod read :code
  [{:keys [state ast] :as env} k {:keys [query]}]
  {:value (get @state k "404: code not found.")})

(defmulti mutate om/dispatch)

