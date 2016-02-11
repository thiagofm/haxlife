(ns haxlife.components.player
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.lambda-coins :as lambda-coins]))

(defui Player
  static om/IQuery
  (query [this]
         [:game/tutorial])
  Object
  (render [this]
          (.log js/console "bug 123")
          (.log js/console (pr-str (om/props this)))
          (let [[id game-tutorial] (first (:game/tutorial (om/props this)))]
            (dom/div nil
                     game-tutorial))))

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
