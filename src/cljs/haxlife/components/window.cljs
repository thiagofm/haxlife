(ns haxlife.components.window
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.data.query :as query]))

(defui Tutorial
  static om/IQuery
  (query [_])
  Object
  (render [this]
          (let [id (om/props this)]
            (dom/div #js {:onClick (fn [e] (om/transact! this `[(~'quit-tutorial {:db/id ~id}) :game/tutorial]))}
                     (dom/p nil "Welcome...")))))

(def tutorial (om/factory Tutorial))

(defui GameWindow
  static om/IQuery
  (query [this]
         [:game/tutorial])
  Object
  (render [this]
          (let [[id game-tutorial] (first (:game/tutorial (om/props this)))]
            (.log js/console game-tutorial)
            (dom/div nil
                      (when game-tutorial (tutorial id))))))
