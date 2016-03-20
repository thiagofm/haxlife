(ns haxlife.components.game-tile
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.components.player :as player]
            [haxlife.components.code :as code]))

(defui ^:once GameTile
  Object
  (render [this]
          (dom/div nil "GameTile"
                   (player/player-comp (om/props this))
                   (code/code-comp (om/props this)))))

(def game-tile-comp (om/factory GameTile))
