(ns work-sessions.components.headers
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]

   [work-sessions.utils :as u]
   ))

(defn instructions []
  [:h4.instructions
   [:p
    "Join a simultaneous work session from your computer using "
    [:a {:href "https://www.google.com/chrome/"
         :target "blank"}
     "Google Chrome"]
    " with "
    [:a {:href "https://chrome.google.com/webstore/detail/google-hangouts/nckgahadagoaajjgafhacjanaoiihapd"
         :target "blank"}
     "Hangouts Chrome extension"]
    " installed. To join a simultaneous work session from your
    smartphone or tablet install the Hangouts app."]])

(defn headers-view []
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
                    :class (str "marquee3k"
                                " "
                                (when (or (:hovered? header)
                                          (:details-visible? header))
                                  "hovered"))
                    }
                   [:span
                    (:text header)]

                   [:span.visible-hovered
                    (unescapeEntities (u/header-details-link-text header))]]]

                 (when (:details-visible? header)
                   (condp = (-> header :details :type)
                     ;;
                     ;; schedule
                     ;;
                     :schedule
                     [:div.details.schedule
                      #_[:hr]
                      [:h4.time-and-place
                       [:a {:href "https://hangouts.google.com/hangouts/_/qrx7a5fwlrhedpmm73amlkrtiye"
                            :target "blank"}
                        (unescapeEntities "Session&nbsp;#1")]
                       (gstring/unescapeEntities ": 26&nbsp;May, 13:30&nbsp;&mdash;&nbsp;14:30&nbsp;Helsinki&nbsp;time")]
                      #_[:hr]
                      [:h4
                       (gstring/unescapeEntities "Session&nbsp;#2 2&nbsp;June, 13:30&nbsp;&mdash;&nbsp;14:30&nbsp;Helsinki&nbsp;time")]
                      #_[:hr]
                      (instructions)
                      #_[:hr]]

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
