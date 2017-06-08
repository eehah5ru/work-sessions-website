(ns work-sessions.components.session-two-page
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]

   [work-sessions.components.headers :refer [headers-view]]
   [work-sessions.utils :as u]

   ))

(defn session-two-page []
  (let [is-docs-visible? (subscribe [:proxy-viewer.docs/is-visible?])]
    (r/create-class
     { :component-did-mount #(dispatch [:ui.header/show-first-details-for-type :session-two])
      :display-name "session-two-page"

      :reagent-render
      (fn []
        [headers-view])})))


;; (defn proxy-viewer-page []
;;   (let [is-docs-visible? (subscribe [:proxy-viewer.docs/is-visible?])]
;;     (fn []
;;       [:div.proxy-viewer
;;        ;; {:class (when @is-docs-visible?
;;        ;;           "docs-visible")}

;;        [headers-view]

;;        (when @is-docs-visible?
;;          [:div.documentation
;;           [:div.responsive-embed.widescreen
;;            [:iframe
;;             {:width 560
;;              :height 315
;;              ;; vnd.youtube://
;;              ;; :src "vnd.youtube://www.youtube.com/watch?v=Qar_jUm7yhQ?autoplay=1"
;;              :src "https://www.youtube.com/embed/TuGG9Wts-zA?autoplay=1"
;;              :frame-border 0
;;              :auto-play 1
;;              :allow-full-screen true}]]])
;;        ])))
