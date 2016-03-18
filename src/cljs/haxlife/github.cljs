(ns haxlife.github
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<! >!]]))


(def github-repositories-url "https://api.github.com/search/repositories")

(defn repositories-res [res]
  (let [body (:body res)
        items (:items body)]
    items))

(defn repositories-req [language]
  (go (let [response (<! (http/get github-repositories-url
                                   {:with-credentials? false
                                    :query-params {"q" (str "language:" language)
                                                   "sort" "stars"
                                                   "order" "desc"}}))]
        (repositories-res response))))

