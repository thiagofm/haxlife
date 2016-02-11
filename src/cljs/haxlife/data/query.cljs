(ns haxlife.data.query
  (:require [datascript.core :as d]
            [om.next :as om]
            [haxlife.lambda-coins :as lambda-coins]))

(defmulti read om/dispatch)

(defmethod read :lambdas/total
  [{:keys [state query]} _ _]
  (.log js/console (pr-str "query done"))
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
  (let [per-second 1]
    {:action (fn []
               (.log js/console "Current state:")
               (.log js/console (pr-str state))
               (.log js/console "Current entity:")
               (.log js/console (pr-str entity))
               (d/transact! state
                                 [(update-in state
                                             [:lambdas/total]
                                             (+ (lambda-coins/next-second per-second)))]))}))


(defmethod mutate 'quit-tutorial
  [{:keys [state]} _ entity]
  (let [id (:db/id entity)]
    ;TODO: refactor to merge with hash :db/id
    {:value {:keys [:game/tutorial]}
     :action (fn []
               (d/transact! state [{:db/id id :game/tutorial false}]))}))
