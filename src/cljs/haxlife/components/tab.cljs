(ns haxlife.components.tab
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui Tab
  static om/IQuery
  (query [this])

  Object
  (render [this]
          (dom/div nil "Tab")))

(def tab-comp (om/factory Tab))
