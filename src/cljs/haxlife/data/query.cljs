(ns haxlife.data.query
  (:require [datascript.core :as d]
            [om.next :as om]
            [haxlife.lambda-coins :as lambda-coins]))

(defmulti read om/dispatch)

(defmethod read :lambdas/total
  [{:keys [state query]} _ _]
  {:value (d/q '[:find ?id ?e
                 :where [?id :lambdas/total ?e]]
               (d/db state) query)})

;(d/q '[:find ?id ?e
;       :where [?id :lambdas/total ?e]]
;     (d/db conn) query)

(defmethod read :game/tutorial
  [{:keys [state query]} _ _]
  {:value (d/q '[:find ?id ?e
                 :where [?id :game/tutorial ?e]]
               (d/db state) query)})

; querying on the REPL
;(d/q '[:find ?id ?e
;       :where [?id :game/tutorial ?e]]
;     (d/db conn) nil)

(defmulti mutate om/dispatch)

(defmethod mutate 'total-next-second
  [{:keys [state]} _ entity]
  {
   :action
   (fn []
     (let [[id total] (first (d/q '[:find ?id ?e :where [?id :lambdas/total ?e]] (d/db state)))]
       (.log js/console (pr-str "hello"))
       (.log js/console (pr-str total))
       (.log js/console (pr-str state))
       (d/transact! state [{:db/id id :lambdas/total (+ total 1)}])))})



(defmethod mutate 'quit-tutorial
  [{:keys [state]} _ entity]
  (let [id (:db/id entity)]
    ;TODO: refactor to merge with hash :db/id
    {:value {:keys [:game/tutorial]}
     :action (fn []
               (d/transact! state [{:db/id id :game/tutorial false}]))}))
