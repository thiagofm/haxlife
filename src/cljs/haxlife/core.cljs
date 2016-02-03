(ns haxlife.core
  (:require [goog.dom :as gdom]
            [om.next :as om :refer-macros [defui]]
            [haxlife.components.counter :as counter]
            [haxlife.data.db :as db]
            [haxlife.data.parser :as data-parser]))


(def reconciler
  (om/reconciler
    {:state db/conn
     :parser (om/parser {:read data-parser/read :mutate data-parser/mutate})}))


(om/add-root! reconciler
  counter/Counter (gdom/getElement "app"))
