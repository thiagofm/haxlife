(ns haxlife.lambda-coins
  (:require [haxlife.data.db :as db]))

(def base-lambdas-per-second 1)
(def base-lambdas-per-character 1)

; Setting up
(db/lambdas-setup base-lambdas-per-second base-lambdas-per-character)

(defn next-second [per-second]
  ; add formula that computes addons
  per-second)
