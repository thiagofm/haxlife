(ns haxlife.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [haxlife.components.counter :as counter]
            [haxlife.components.window :as window]
            [haxlife.data.db :as db]
            [haxlife.data.query :as query]))


(def reconciler
  (om/reconciler
    {:state db/conn
     :parser (om/parser {:read query/read :mutate query/mutate})}))


(om/add-root! reconciler window/Window (gdom/getElement "app"))

; (js/setInterval (fn[] (om/transact! reconciler `[(~'total-next-second)])) 1000)

