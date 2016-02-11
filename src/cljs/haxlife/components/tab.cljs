(ns haxlife.components.tab
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.data.query :as query]))

(defui StoreTab
  Object
  (render [this]
          (dom/div nil "StoreTab")))

(def store-tab (om/factory StoreTab))

(defui Tab
  Object
  (render [this]
          (dom/div nil
                   (dom/p nil "Tabs")
                   ; this will be in the future the current selected tab
                   (store-tab))))

(def tab-comp (om/factory Tab))
