(ns haxlife.components.tab
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [haxlife.utils :as utils]
            [haxlife.data.query :as query]))

(defui StoreTab
  Object
  (render [this]
          (dom/div nil "StoreTab")))


(def store-tab-comp (om/factory StoreTab))

(defui SkilltreeTab
  Object
  (render [this]
          (dom/div nil "SkilltreeTab")))

(def skilltree-tab-comp (om/factory SkilltreeTab))

(defui TabLink
  static om/IQuery
  (query [this]
         [:game/active-tab-component])

  Object
  (render [this]
    (let [{:keys [name]} (om/get-computed this)]
      (dom/li
       #js {:onClick (fn [e]
                       (om/transact! (om/get-reconciler this) `[(~'set-active-tab-component {:active-tab-comp ~name}) :game/active-tab-component]))}
       name))))

(def tab-link-comp (om/factory TabLink {:keyfn (fn [props] (:name (om/get-computed props)))}))

(defui Tab
  static om/IQuery
  (query [this]
         [:game/active-tab-component])

  Object
  (render [this]
          (let [active-tab-component (:game/active-tab-component (om/props this))]
            (dom/div nil
                     (dom/ul nil
                             (tab-link-comp (om/computed (om/props this) {:name "Store"}))
                             (tab-link-comp (om/computed (om/props this) {:name "Skilltree"})))
                     (case active-tab-component
                       "Store" (store-tab-comp (om/props this))
                       "Skilltree" (skilltree-tab-comp (om/props this)))))))

(def tab-comp (om/factory Tab))
