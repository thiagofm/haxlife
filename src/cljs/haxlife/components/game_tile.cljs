(ns haxlife.components.game-tile
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.data.query :as query]
            [haxlife.components.player :as player]
            [haxlife.components.code :as code]))

(defui GameTile
  Object
  (render [this]
          (dom/div nil "GameTile"
          (player/player-comp)
          (code/code-comp))))

(def game-tile-comp (om/factory GameTile))
