(ns haxlife.components.player
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.lambda-coins :as lambda-coins]))

(defui Player
  static om/IQuery
  (query [this]
         [:lambdas/total])

  Object
  (componentWillMount [this]
    (let [[id total] (first (:lambdas/total (om/props this)))]
      ; Getting the reconciler here is needed because of a bug that it doesn't find the query from this component
        (js/setInterval (fn[] (om/transact! (om/get-reconciler this) `[(~'total-next-second) :lambdas/total])) 1000)))

  (render [this]
          (let [[id total] (first (:lambdas/total (om/props this)))]
            (dom/div nil
                     total))))

;; (defui Player
;;   static om/IQuery
;;   (query [this]
;;          '[:game/tutorial])
;;   Object
;;   (render [this]
;;           (let [[id total] (first (:game/tutorial (om/props this)))]
;;             (.log js/console (pr-str total))
;;             (.log js/console (pr-str (om/props this)))
;;             (dom/div nil
;;                      (cons "Player lambdas" total)))))

(def player-comp (om/factory Player))
