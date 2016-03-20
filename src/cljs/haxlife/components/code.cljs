(ns haxlife.components.code
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui ^:once Code
  static om/IQuery
  (query [this]
         '[(:code {:query "brasil"})])

  Object
  (render [this]
    (let [{:keys [code]} (om/props this)]
      (.log js/console (pr-str code))
      (dom/div #js {:id "code"} code))))

(def code-comp (om/factory Code))

