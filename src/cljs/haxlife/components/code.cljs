(ns haxlife.components.code
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.data.query :as query]))

(defui Code
  static om/IQueryParams
  (params [this])

  static om/IQuery
  (query [this])

  Object
  (render [this]
          (let [language (:language (om/get-computed this))]
            ; TODO: refactor this when remotes + datascript start to work well together
            (om/transact! (om/get-reconciler this) `[(~'set-github-file {:language ~language})])

            (dom/div nil "hello")))) ;

(def code-comp (om/factory Code))

