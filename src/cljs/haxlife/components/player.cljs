(ns haxlife.components.player
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.lambda-coins :as lambda-coins]))

(defn- set-interval [reconciler]
  (js/setInterval (fn[] (om/transact! reconciler `[(~'total-next-second) :lambdas/total])) 1000))

(defn- clear-interval [interval-id]
  (js/clearInterval interval-id))

(defui Player
  static om/IQuery
  (query [this]
         [:lambdas/total :lambdas/interval-id])

  Object
  ; Sets a interval where the player total will have its lambdas per second applied
  ; Unmounting is REALLY necessary due to saves/reloads from Figwheel breaking dev cycle
  (componentWillMount [this]
    (let [[id total] (first (:lambdas/total (om/props this)))
          interval-id (set-interval (om/get-reconciler this))]
      (om/transact! (om/get-reconciler this) `[(~'set-interval-id {:interval-id ~interval-id})])))
  (componentWillUnmount [this]
    (let [interval-id (ffirst (:lambdas/interval-id (om/props this)))]
      (clear-interval interval-id)))

  (render [this]
          (let [[id total] (first (:lambdas/total (om/props this)))]
            (dom/div nil total))))

(def player-comp (om/factory Player))
