(ns haxlife.components.player
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.data.query :as query]))

(defui Player
  Object
  (render [this]
          (dom/div nil "Player")))

(def player-comp (om/factory Player))
