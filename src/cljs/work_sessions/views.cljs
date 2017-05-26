(ns work-sessions.views
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]

   [work-sessions.pages :refer [pages path-for]]

   ;;
   ;; pages
   ;;
   [work-sessions.components.home-page :refer [home-page]]
   [work-sessions.components.schedule-page :refer [schedule-page]]
   [work-sessions.components.about-page :refer [about-page]]
   [work-sessions.components.proxy-viewer-page :refer [proxy-viewer-page]]
   ))


;; (defn index []
;;   [:div.index
;;    #_(for [i (repeat 30 "Simultaneous Work Sessions")
;;            n (range 30)]
;;        ^{:key n}
;;        [:h1.marquee
;;         [:span i]])
;;    (map #(with-meta
;;            [:h1.marquee3k
;;             (merge
;;              {:on-click (fn []
;;                           (dispatch [:ui.header/click]))
;;               :data-pausable true}
;;              %3)
;;             [:span
;;              ;; {:on-click (fn []
;;              ;;              (dispatch [:ui.header/click]))}
;;              %1]]
;;            {:key %2})
;;         (repeat 30 "Simultaneous Work Sessions")
;;         (range 30)
;;         (repeatedly
;;          #(hash-map :data-reverse (if (> 0.5 (rand)) true false)
;;                     :data-speed (+ 30 (* 70 (rand)))
;;                     )))])

;;;
;;; main panel
;;;
(defn main-panel []
  (let [current-page (subscribe [:ui.page/current])
        splash-screen-state (subscribe [:ui.splash-screen/state])]
    (fn []
      [:div.main-panel

       ;; (when (not= @splash-screen-state
       ;;          :disabled)
       ;;   [:div.splash-screen
       ;;    {:class (condp = @splash-screen-state
       ;;              :enabling
       ;;              "enabling"

       ;;              :disabling
       ;;              "disabling"

       ;;              "")}])

       (when-let [page (pages (:handler @current-page))]
         [page])]
      )))
