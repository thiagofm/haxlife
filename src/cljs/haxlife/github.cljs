(ns haxlife.github
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [om.next :as om :include-macros true]
            [clojure.string :as string]
            [cljs.core.async :refer [<! >! put! chan]])
  (:import [goog Uri]
           [goog.net Jsonp]))


(def github-repositories-url "https://api.github.com/search/repositories")

(def base-url
  "http://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=")


(defn jsonp
  ([uri] (jsonp (chan) uri))
  ([c uri]
   (let [gjsonp (Jsonp. (Uri. uri))]
     (.send gjsonp nil #(put! c %))
     c)))

(defn send-to-chan [c]
  (fn [{:keys [code]} cb]
    (.log js/console (pr-str cb))
    (when code
      (let [{[search] :children} (om/query->ast code)
            query (get-in search [:params :query])]
        (put! c [query cb])))))

(defn code-loop [c]
  (go
    (loop [[query cb] (<! c)]
      (let [[_ results] (<! (jsonp (str base-url query)))]
        (cb {:code results}))
      (recur (<! c)))))

(defn repositories [language repository-response cb]
  (go
    (if (nil? repository-response)
      (let [response (<! (http/get github-repositories-url {:with-credentials? false
                                                            :query-params {"q" (str "language:" language)
                                                                           "sort" "stars"
                                                                           "order" "desc"}}))]
        (cb (:items (:body response))))
      (cb repository-response))))
