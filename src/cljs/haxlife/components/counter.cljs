(ns haxlife.components.counter
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui Counter
  static om/IQuery
  (query [this]
         [{:app/counter [:db/id :app/title :app/count]}])
  Object
  (render [this]
          (let [{:keys [app/title app/count] :as entity}
                (get-in (om/props this) [:app/counter 0])]
            (dom/div nil
                     (dom/h2 nil title)
                     (dom/span nil (str "Count: " count))
                     (dom/button
                      #js {:onClick
                           (fn [e]
                             (om/transact! this
                                           `[(app/increment ~entity)]))}
                      "Click me!")))))
