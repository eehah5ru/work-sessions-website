(ns work-sessions.history
  (:require
   [re-frame.core :as re-frame :refer [dispatch subscribe]]

   [pushy.core :as pushy]
   [bidi.bidi :as bidi]

   [work-sessions.pages :refer [routes]]
   ))

(def history
  (pushy/pushy #(dispatch [:ui.page/set-current %]) (partial bidi/match-route routes)))

(defn start! []
  (pushy/start! history))


(defn set-current! [path]
  (pushy/set-token! history path))
