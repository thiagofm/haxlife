(ns haxlife.components.window
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.components.game-tile :as game-tile]
            [haxlife.components.action-tile :as action-tile]))

(defui GameWindow
  static om/IQuery
  (query [this])

  Object
  (render [this]
          (dom/div nil
                   "Game window"
                   (game-tile/game-tile-comp (om/props this))
                   (action-tile/action-tile-comp (om/props this)))))

(def game-window-comp (om/factory GameWindow))

(defui Window
  Object
  (render [this]
          (dom/div nil "Hello world"
                   (game-window-comp (om/props this)))))
