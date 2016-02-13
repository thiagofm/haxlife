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

(defmethod read :lambdas/interval-id
  [{:keys [state query]} _ _]
  {:value (d/q '[:find ?interval-id
                 :where [_ :lambdas/interval-id ?interval-id]]
               (d/db state) query)})
;;;;;;

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

(defmethod mutate 'set-interval-id
  [{:keys [state]} _ entity]
  {:action
   (fn[]
     (let [id (ffirst (d/q '[:find ?id :where [?id :lambdas/interval-id _]]
                           (d/db state)))
           interval-id (:interval-id entity)]
       (d/transact! state [{:db/id id :lambdas/interval-id interval-id}])))})

(defmethod mutate 'total-next-second
  [{:keys [state]} _ entity]
  {:action
   (fn []
     (let [[id total] (first (d/q '[:find ?id ?e :where [?id :lambdas/total ?e]] (d/db state)))
           per-second (ffirst (d/q '[:find ?e :where [_ :lambdas/per-second ?e]] (d/db state)))
           next-second-total (lambda-coins/next-second per-second)]
       (d/transact! state [{:db/id id :lambdas/total (+ total next-second-total)}])))})


; (d/transact! conn [{:db/id 2 :lambdas/per-second 100}])

(defmethod mutate 'quit-tutorial
  [{:keys [state]} _ entity]
  (let [id (:db/id entity)]
    ;TODO: refactor to merge with hash :db/id
    {:value {:keys [:game/tutorial]}
     :action (fn []
               (d/transact! state [{:db/id id :game/tutorial false}]))}))
