(ns work-sessions.components.about-page
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]

   [work-sessions.components.headers :refer [headers-view]]
   ))

(defn about-page []
  (r/create-class
   {:component-did-mount #(dispatch [:ui.header/show-first-details-for-type :description])
    :display-name "about-page"

    :reagent-render
    (fn []
      [headers-view])}))
;; (defn about-page []
;;   [headers-view])
