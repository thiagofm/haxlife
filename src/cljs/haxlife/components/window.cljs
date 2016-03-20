(ns haxlife.components.window
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.data.query :as query]
            [haxlife.components.tab :as tab]
            [haxlife.components.action-tile :as action-tile]
            [haxlife.components.game-tile :as game-tile]
            [haxlife.components.player :as player]
            [haxlife.components.code :as code]))

(defui Tutorial
  static om/IQuery
  (query [_])
  Object
  (render [this]
          (let [id (om/props this)]
            (dom/div #js {:onClick (fn [e] (om/transact! this `[(~'quit-tutorial {:db/id ~id}) :game/tutorial]))}
                     (dom/p nil "Welcome...")))))

(def tutorial-comp (om/factory Tutorial))

(defui GameWindow
  static om/IQuery
  (query [this])

  Object
  (render [this]
          (dom/div nil
                   (game-tile/game-tile-comp (om/props this))
                   (action-tile/action-tile-comp (om/props this)))))

(def game-window-comp (om/factory GameWindow))

(defui Window
  static om/IQuery
  (query [this]
         [:game/tutorial
          (first (om/get-query tab/Tab))
          (first (om/get-query tab/TabLink))
          (first (om/get-query player/Player))
          (second (om/get-query player/Player))
          (nth (om/get-query player/Player) 2)])

  Object
  (render [this]
          (let [[id game-tutorial] (first (:game/tutorial (om/props this)))]
            (dom/div nil
                     (if game-tutorial
                       (tutorial-comp id)
                       (game-window-comp (om/props this)))))))
