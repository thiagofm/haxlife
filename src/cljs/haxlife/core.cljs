(ns haxlife.core
  (:require [goog.dom :as gdom]
            [om.next :as om]
            [haxlife.reconciler :as reconciler]
            [haxlife.components.window :as window]))

(om/add-root! reconciler/reconciler window/Window (gdom/getElement "app"))
