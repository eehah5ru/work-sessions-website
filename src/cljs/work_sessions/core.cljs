(ns work-sessions.core
  (:require
   [cljs-time.extend]
   [cljsjs.react-flexbox-grid]
   ;; [madvas.re-frame.google-analytics-fx]
   [work-sessions.handlers]
   [work-sessions.subs]
   [work-sessions.views :as views]

   ;; [childrensfutures-trade.utils :as u]
   [print.foo.preloads.devtools]
   [re-frame.core :as re-frame :refer [dispatch subscribe]]
   [reagent.core :as reagent]
   [pushy.core :as pushy]
   [bidi.bidi :as bidi]
   [madvas.re-frame.google-analytics-fx :as google-analytics-fx]

   [work-sessions.history :as history]))

(enable-console-print!)



(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []

  #_(when (not childrensfutures-trade.utils.DEV)
    (google-analytics-fx/set-enabled! (not childrensfutures-trade.utils.DEV)))

  (.addEventListener js/window
                     "load"
                     (fn []
                       (re-frame/dispatch-sync [:initialize])
                       (history/start!)
                       (mount-root)))
  #_(.addEventListener js/window "resize" #(dispatch [:ui.window/resize]))
  )
