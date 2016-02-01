(ns haxlife.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [haxlife.core-test]))

(enable-console-print!)

(doo-tests 'haxlife.core-test)
