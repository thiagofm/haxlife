(ns haxlife.core
  (:require [om.next :as om :include-macros true]
            [haxlife.components.window :as window]
            [goog.dom :as gdom]
            [haxlife.query :as query]))

(enable-console-print!)

(def app-state (atom))

(def reconciler
  (om/reconciler {:state app-state
                  :parser (om/parser {:read query/read :mutate query/mutate})}))

(om/add-root! reconciler window/Window (gdom/getElement "app"))
