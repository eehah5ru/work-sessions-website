(ns work-sessions.components.proxy-viewer-page
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]

   [work-sessions.components.headers :refer [headers-view]]
   [work-sessions.utils :as u]

   ))

(defn proxy-viewer-page []
  (let [is-docs-visible? (subscribe [:proxy-viewer.docs/is-visible?])]
    (r/create-class
     {;;:component-did-mount #(dispatch [:ui.header/show-first-details-for-type :schedule])
      :display-name "proxy-viewer-page"

      :reagent-render
      (fn []
        [:div.proxy-viewer
         ;; {:class (when @is-docs-visible?
         ;;           "docs-visible")}

         [headers-view]

         [:div.documentation-container
          [:div.documentation
           [:div.responsive-embed.widescreen
            [:iframe
             {:width 560
              :height 315
              ;; vnd.youtube://
              ;; :src "vnd.youtube://www.youtube.com/watch?v=Qar_jUm7yhQ?autoplay=1"
              :src "https://www.youtube.com/embed/wd49EhHutPk?autoplay=1&loop=1&controls=1&playlist=wd49EhHutPk"
              :frame-border 0
              :auto-play 1
              :loop 1
              :controls 1
              ;; :start 1320
              ;; :end 1340
              ;; :end 3900
              :allow-full-screen true}]]]]])})))


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
