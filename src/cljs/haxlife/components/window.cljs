(ns haxlife.components.window
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.components.game-tile :as game-tile]
            [haxlife.components.action-tile :as action-tile]
            [haxlife.components.code :as code]))

(defui GameWindow
  Object
  (render [this]
          (dom/div #js {:id "game-window"}
                   "Game window"
                   (game-tile/game-tile-comp (om/props this))
                   (action-tile/action-tile-comp (om/props this)))))

(def game-window-comp (om/factory GameWindow))

(defui Window
  static om/IQuery
  (query [this]
         [(first (om/get-query code/Code))])

  Object
  (render [this]
          (.log js/console (pr-str (om/props this)))
          (dom/div nil "Hello world"
                   (game-window-comp (om/props this)))))
