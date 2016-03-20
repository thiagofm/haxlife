(ns haxlife.data.query
  (:require [datascript.core :as d]
            [om.next :as om]
            [haxlife.github :as github]
            [haxlife.lambda-coins :as lambda-coins]))

(defmulti read om/dispatch)

(defmethod read :values
  [{:keys [state query]} key params]
  {:value
   (let [keys (:keys params)]
     (into {} (d/q '[:find ?c ?e
            :in $ [?c ...]
            :where [_ ?c ?e]]
          (d/db state) keys)))})

(defmethod read :game/active-tab-component
  [{:keys [state query]} _ _]
  {:value (d/q '[:find ?e .
                 :where [_ :game/active-tab-component ?e]]
               (d/db state) query)})

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

(defmethod mutate 'set-active-tab-component
  [{:keys [state]} _ entity]
  {:action
   (fn[]
     (let [id (ffirst (d/q '[:find ?id :where [?id :game/active-tab-component _]]
                           (d/db state)))
           active-tab-comp (:active-tab-comp entity)]
       (d/transact! state [{:db/id id :game/active-tab-component active-tab-comp}])))})

(defmethod mutate 'total-next-second
  [{:keys [state]} _ entity]
  {:action
   (fn []
     (let [[id total] (first (d/q '[:find ?id ?e :where [?id :lambdas/total ?e]] (d/db state)))
           per-second (ffirst (d/q '[:find ?e :where [_ :lambdas/per-second ?e]] (d/db state)))
           next-second-total (lambda-coins/next-second per-second)]
       (d/transact! state [{:db/id id :lambdas/total (+ total next-second-total)}] :values)))})

(defmethod mutate 'quit-tutorial
  [{:keys [state]} _ entity]
  (let [id (:db/id entity)]
    {:value {:keys [:game/tutorial]}
     :action (fn []
               (d/transact! state [{:db/id id :game/tutorial false}]))}))

(defmethod mutate 'set-github-file
  [{:keys [state]} _ entity];
  {:action
   (let [language "ruby"
         repositories-key (keyword "github/repositories" language)
         repository-response (d/q '[:find ?e . :in $ [?c ...] :where [_ ?c ?e]] (d/db state) [repositories-key])]
     (.log js/console repository-response)
     (if (nil? repository-response)
      (github/repositories language
        (fn [response]
          (d/transact! state [{:db/id -1 repositories-key response}])))
      repository-response))})
