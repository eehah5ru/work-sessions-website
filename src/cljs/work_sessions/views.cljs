(ns work-sessions.views
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring]
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

(defn index []
  (let [headers (subscribe [:headers/all])]
    (fn []
      [:div.index
       (map (fn [header h-id]
              (with-meta
                [:div.header-container
                 [:div.header
                  (when (:angled? header)
                    {:style {:transform (str "rotate(" (:angle header) "deg)")}})

                  [:h1
                   {:on-click #(dispatch [:ui.header/click h-id])
                    :on-mouse-enter #(dispatch [:ui.header/hover h-id])
                    :on-mouse-leave #(dispatch [:ui.header/hover h-id])
                    :data-pausable true
                    :data-reverse (:reverse? header)
                    :data-speed (:speed header)
                    :class (str "marquee3k" " " (when (:hovered? header) "hovered"))
                    }
                   [:span
                    (:text header)]
                   [:span.visible-hovered
                    (condp = (:details header)
                      :schedule
                      (gstring/unescapeEntities "&nbsp;->&nbsp;Schedule&nbsp;->&nbsp;")

                      :description
                      (gstring/unescapeEntities "&nbsp;->&nbsp;About&nbsp;->&nbsp;")

                      :documentation
                      (gstring/unescapeEntities "&nbsp;->&nbsp;Archive&nbsp;->&nbsp;"))]
                   ]]
                 (when (:details-visible? header)
                   (condp = (:details header)
                     ;;
                     ;; schedule
                     ;;
                     :schedule
                     [:div.details.schedule
                      [:h4
                       (gstring/unescapeEntities "Session&nbsp;#1: 26&nbsp;May, 13:30&thinsp;&mdash;&thinsp;14:30&nbsp;Helsinki&nbsp;time")
                       [:br]
                       [:a {:href "https://hangouts.google.com/hangouts/_/qrx7a5fwlrhedpmm73amlkrtiye"
                            :target "blank"}
                        "JOIN"]]
                      [:br]
                      [:h4
                       (gstring/unescapeEntities "Session&nbsp;#2 2&nbsp;June, 13:30&thinsp;&mdash;&thinsp;14:30&nbsp;Helsinki&nbsp;time")]]

                     ;;
                     ;; documentation
                     ;;
                     :documentation
                     [:div.details.documentatation
                      [:div.responsive-embed.widescreen
                       [:iframe
                        {:width 560
                         :height 315
                         ;; vnd.youtube://
                         ;; :src "vnd.youtube://www.youtube.com/watch?v=Qar_jUm7yhQ?autoplay=1"
                         :src "https://www.youtube.com/embed/Qar_jUm7yhQ?autoplay=1"
                         :frame-border 0
                         :auto-play 1
                         :allow-full-screen true}]]]

                     ;;
                     ;; description
                     ;;
                     :description
                     [:h4.details.description
                      [:p "In a series of simultaneous work sessions,
                                            we invite you to join a
                                            work activity for an hour
                                            space of time."]

                      [:p "The synchronized sessions are designed to
                      embody participants into one working mode. Being
                      united by one of online-communication tools, we
                      will simply work and listen to each other's
                      work. Being silently present in the imaginary
                      space."]
                      [:p "Rationalized time management of production
                processes is the contrast to joint silent sessions,
                when we unite not to produce something together, but
                rather to help each other to overcome the accelerating
                communication and production processes reinforced by
                online tools. Using the same tools not for their
                primarily functions, we are constructing together the
                union of non-material workers."]]))]  {:key h-id}))
            @headers
            (range (count @headers)))])))
