(ns haxlife.components.player
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui Player
  Object
  (render [this]
          (dom/div nil "Player")))

(def player-comp (om/factory Player))

