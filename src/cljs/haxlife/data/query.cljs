(ns haxlife.data.query
  (:require [datascript.core :as d]
            [om.next :as om]))

(defmulti read om/dispatch)

(defmethod read :game/tutorial
  [{:keys [state query]} _ _]
  {:value (d/q '[:find ?id ?e :where [?id :game/tutorial ?e]] (d/db state) query)})

(defmethod read :app/counter
  [{:keys [state query]} _ _]
  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :app/title]]
               (d/db state) query)})

(defmulti mutate om/dispatch)

(defmethod mutate 'quit-tutorial
  [{:keys [state]} _ entity]
  (let [id (:db/id entity)]
    ;TODO: refactor to merge with hash :db/id
    {:value {:keys [:game/tutorial]}
     :action (fn []
               (d/transact! state [{:db/id id :game/tutorial false}]))}))

(defmethod mutate 'app/increment
  [{:keys [state]} _ entity]
  {:value {:keys [:app/counter]}
   :action (fn [] (d/transact! state
                               [(update-in entity [:app/count] inc)]))})
