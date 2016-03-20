(ns haxlife.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.next :as om :include-macros true]
            [haxlife.components.window :as window]
            [goog.dom :as gdom]
            [clojure.string :as string]
            [haxlife.query :as query]
            [cljs.core.async :refer [<! >! put! chan]])
  (:import [goog Uri]
           [goog.net Jsonp]))

(enable-console-print!)

(def base-url
  "http://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=")

(defn jsonp
  ([uri] (jsonp (chan) uri))
  ([c uri]
   (let [gjsonp (Jsonp. (Uri. uri))]
     (.send gjsonp nil #(put! c %))
     c)))

(def app-state (atom))
(def send-chan (chan))

(defn search-loop [c]
  (go
    (loop [[query cb] (<! c)]
      (let [[_ results] (<! (jsonp (str base-url query)))]
        (cb {:code results}))
      (recur (<! c)))))

(defn send-to-chan [c]
  (fn [{:keys [search]} cb]
    (when search
      (let [{[search] :children} (om/query->ast search)
            query (get-in search [:params :query])]
        (put! c [query cb])))))

(def reconciler
  (om/reconciler {:state app-state
                  :parser (om/parser {:read query/read :mutate query/mutate})
                  :send (send-to-chan send-chan)
                  :remotes [:remote :search]}))

(search-loop send-chan)

(om/add-root! reconciler window/Window (gdom/getElement "app"))
