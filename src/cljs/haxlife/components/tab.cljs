(ns haxlife.components.tab
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.utils :as utils]
            [haxlife.data.query :as query]))

(defui StoreTab
  Object
  (render [this]
          (dom/div nil "StoreTab")))


(defui StoreTabLink
  static om/IQuery
  (query [_])
  Object
  (render [this]
          (dom/li
           #js {:onClick (fn [e]
                       (om/transact! this `[(~'set-active-tab-component {:active-tab-comp "store"}) :game/active-tab-component]))}
           nil
           "Store")))

(def store-tab-comp (om/factory StoreTab))
(def store-tab-link-comp (om/factory StoreTabLink))

(defui SkilltreeTab
  Object
  (render [this]
          (dom/div nil "SkilltreeTab")))

(defui SkilltreeTabLink
  static om/IQuery
  (query [_])
  Object
  (render [this]
          (dom/li
           #js {:onClick (fn [e]
                           (om/transact! this `[(~'set-active-tab-component {:active-tab-comp "skilltree"}) :game/active-tab-component]))}
           nil
           "Skilltree")))

(def skilltree-tab-comp (om/factory SkilltreeTab))
(def skilltree-tab-link-comp (om/factory SkilltreeTabLink))

(defui Tab
  static om/IQuery
  (query [this]
         [:game/active-tab-component])
  Object
  (render [this]
          (let [active-tab-component (:game/active-tab-component (om/props this))]
            (dom/div nil
                     (dom/ul nil
                             (store-tab-link-comp)
                             (skilltree-tab-link-comp))
                     (case active-tab-component
                       "store" (store-tab-comp (om/props this))
                       "skilltree" (skilltree-tab-comp (om/props this)))))))

(def tab-comp (om/factory Tab))
