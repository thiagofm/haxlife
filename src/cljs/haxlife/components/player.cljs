(ns haxlife.components.player
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.lambda-coins :as lambda-coins]))

(defui Player
  static om/IQuery
  (query [this]
         [:lambdas/total])
  Object
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
