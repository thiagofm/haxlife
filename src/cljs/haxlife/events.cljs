(ns haxlife.events
  (:require [om.next :as om]
            [haxlife.lambda-coins :as lambda-coins]))

(defn update-total-interval [reconciler]
  (js/setInterval (fn[] (om/transact! reconciler `[(~'total-next-second)])) 1000))
