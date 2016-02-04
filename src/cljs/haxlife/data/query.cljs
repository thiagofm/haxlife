(ns haxlife.data.parser
  (:require [datascript.core :as d]
            [om.next :as om]))

(defmulti read om/dispatch)

(defmethod read :game/tutorial
  [{:keys [state query]} _ _]
  {:value (d/q '[:find ?e . :where [_ :game/tutorial ?e]] (d/db state))})

(defmethod read :app/counter
  [{:keys [state query]} _ _]
  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :app/title]]
               (d/db state) query)})

(defmulti mutate om/dispatch)

(defmethod mutate 'app/increment
  [{:keys [state]} _ entity]
  {:value {:keys [:app/counter]}
   :action (fn [] (d/transact! state
                               [(update-in entity [:app/count] inc)]))})
