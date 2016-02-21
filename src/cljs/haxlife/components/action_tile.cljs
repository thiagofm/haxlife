(ns haxlife.components.action-tile
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.data.query :as query]
            [haxlife.components.tab :as tab]))

(defui ActionTile
  Object
  (render [this]
          (dom/div nil "ActionTile" (tab/tab-comp (om/props this)))))

(def action-tile-comp (om/factory ActionTile))
