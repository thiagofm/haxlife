(ns haxlife.core
  (:require [om.next :as om :include-macros true]
            [haxlife.components.window :as window]
            [goog.dom :as gdom]
            [haxlife.query :as query]
            [haxlife.github :as github]
            [cljs.core.async :refer [chan]]))

(enable-console-print!)

(def app-state (atom))
(def send-chan (chan))

(github/code-loop send-chan)

(def reconciler
  (om/reconciler {:state app-state
                  :parser (om/parser {:read query/read :mutate query/mutate})
                  :send (github/send-to-chan send-chan)
                  :remotes [:remote :code]}))



(om/add-root! reconciler window/Window (gdom/getElement "app"))
