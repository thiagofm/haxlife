(ns haxlife.github
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<! >!]]))


(def github-repositories-url "https://api.github.com/search/repositories")

(defn repositories [language repository-response cb]
  (go
    (if (nil? repository-response)
      (let [response (<! (http/get github-repositories-url {:with-credentials? false
                                                            :query-params {"q" (str "language:" language)
                                                                           "sort" "stars"
                                                                           "order" "desc"}}))]
        (cb (:items (:body response))))
      (cb repository-response))))
