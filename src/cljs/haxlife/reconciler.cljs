(ns haxlife.reconciler
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :as async :refer [<! >! put! chan]]
            [om.next :as om]
            [haxlife.data.query :as query]
            [haxlife.data.db :as db]))

(def send-chan (chan))

(defn query-loop [c]
  (go
    (loop [[query cb] (<! c)]
      (.log js/console "Query loop")
      (.log js/console (pr-str c))
      (.log js/console (pr-str query))
      (.log js/console (pr-str cb))
      (cb {:remote/github-repositories 1})
      (recur (<! c)))))

(query-loop send-chan)

(defn send-to-chan [c]
  (fn [cb]
    (let [read-method (first (:remote cb))]
      (.log js/console "Send to chan")
      (.log js/console (pr-str c))
      (.log js/console (pr-str cb))
      (put! c [read-method cb]))))

(def reconciler
  (om/reconciler
   {:state db/conn
    :send (send-to-chan send-chan)
    :parser (om/parser {:read query/read :mutate query/mutate})}))

