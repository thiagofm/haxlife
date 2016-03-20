(ns haxlife.components.code
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui ^:once Code
  static om/IQuery
  (query [this]
         [:code])

  Object
  (render [this]
    (let [{:keys [code]} (om/props this)]
      (dom/div #js {:id "code"} code))))

(def code-comp (om/factory Code))

