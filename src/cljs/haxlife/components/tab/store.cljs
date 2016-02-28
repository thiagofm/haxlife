(ns haxlife.components.tab.store
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui Store
  Object
  (render [this]
          (dom/div nil "Store")))

(def store-comp (om/factory Store))
