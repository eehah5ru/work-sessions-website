(ns work-sessions.components.schedule-page
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]
   ))

(defn schedule-page []
  [:h1 "Schedule"])
