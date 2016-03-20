(ns haxlife.query
  (:require [om.next :as om]))

(defmulti read om/dispatch)
(defmulti mutate om/dispatch)
