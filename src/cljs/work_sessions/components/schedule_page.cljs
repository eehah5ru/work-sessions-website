(ns work-sessions.components.schedule-page
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]
   [work-sessions.components.headers :refer [headers-view]]
   ))

(defn schedule-page []
  [headers-view])
