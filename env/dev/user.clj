(ns user
  (:require [figwheel-sidecar.repl-api]
            [work-sessions.core]
            [ring.middleware.reload :refer [wrap-reload]]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def http-handler
  (wrap-reload #'work-sessions.core/http-handler))
