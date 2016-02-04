(ns haxlife.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [haxlife.components.counter :as counter]
            [haxlife.components.window :as window]
            [haxlife.data.db :as db]
            [haxlife.data.parser :as data-parser]))


(def reconciler
  (om/reconciler
    {:state db/conn
     :parser (om/parser {:read data-parser/read :mutate data-parser/mutate})}))


(om/add-root! reconciler
  window/GameWindow (gdom/getElement "app"))
