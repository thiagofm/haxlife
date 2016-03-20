(ns haxlife.components.code
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui ^:once Code
  Object
  (render [this]
          (dom/div nil "Code")))

(def code-comp (om/factory Code))

