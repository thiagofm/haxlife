(ns haxlife.query
  (:require [om.next :as om]
            [clojure.string :as string]))

(defmulti read om/dispatch)

;; (defmethod read :code
;;   [{:keys [state ast] :as env} k {:keys [query]}]
;;   {:value (get @state k "404: code not found.")})

(defmethod read :code
  [{:keys [state ast] :as env} k {:keys [query]}]
  (merge
   {:value (get @state k [])}
   (when-not (or (string/blank? query)
                 (< (count query) 3))
     {:search ast})))

(defmulti mutate om/dispatch)

