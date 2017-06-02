(ns work-sessions.components.session-not-found-page
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]

   ;; [work-sessions.components.headers :refer [headers-view]]
   ))

(defn session-not-found-page []
  [:div.session-not-found
   [:h4
    "It's too early! Please follow this "
    [:a {:href "http://sws.eeefff.org/session-two-room"}
     "link"]
    " on 2 June 13:30 Helsinki time"]])
