(ns haxlife.components.tab.skilltree
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui Skilltree
  Object
  (render [this]
          (dom/div nil "Skilltree")))

(def skilltree-comp (om/factory Skilltree))
