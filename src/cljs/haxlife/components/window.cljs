(ns haxlife.components.window
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui Tutorial
  Object
  (render [_]
          (dom/p nil "Welcome...")))

(def tutorial (om/factory Tutorial))

(defui GameWindow
  static om/IQuery
  (query [this]
         [:game/tutorial])
  Object
  (render [this]
          (let [game-tutorial (om/props this)]
            (dom/div nil
                      (when (:game/tutorial game-tutorial) (tutorial))))))
