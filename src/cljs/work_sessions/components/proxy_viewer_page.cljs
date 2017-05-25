(ns work-sessions.components.proxy-viewer-page
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]

   [work-sessions.components.headers :refer [headers-view]]
   [work-sessions.utils :as u]

   ))

(defn proxy-viewer-page []
  [headers-view])
