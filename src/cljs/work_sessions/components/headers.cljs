(ns work-sessions.components.headers
  (:require
   [re-frame.core :refer [dispatch subscribe]]
   [reagent.core :as r]
   [goog.string :as gstring :refer [unescapeEntities]]

   [work-sessions.utils :as u]
   ))

(defn instructions [session-room-url]
  [:h4.instructions
   [:p
    "EN"]
   [:p
    "You can join by the "
    [:a {:href (str "http://sws.eeefff.org" session-room-url)
         :target "_blank"}
     "link"]
    "."]
   [:p
    (unescapeEntities "A number of participants in one session&nbsp;&mdash;&nbsp;up to 10 people.")]
   [:p
    "This is a digital union of non-material workers, the virtual space of multi-user processing, so try not to distract each other, you can watch someone doing her cognitive work."]
   [:p
    "You can be present with an image of you, you can also share your screen with your current work (On the left side of the screen, click Share screen – a button with a green computer). The sound stays on."]
   ;;
   ;; session description
   ;;
   [:p
    "In the third session we are concentrating on back-ups, or rather on what is lost irrevocably, if a computer or a tablet suddenly breaks or a phone is stolen. To get an idea of what exactly you should do, spend a few minutes remembering the data that you have irretrievably lost. These can be texts that once disappeared from the desktop, and their copies were not found. The editing of the video that you have been doing for the whole day was lost when the editing program collapsed one evening, and the file hasn’t been saved. Formatted flash drive with photos from the shooting that you haven’t copied anywhere. Or a broken site due to lost data during a hosting crash."]
   [:p
    (unescapeEntities "The session length is 1 hour, 13.30&nbsp;&mdash;&nbsp;14.30 Helsinki and Moscow time.")]
   [:p
    (unescapeEntities "The next session will be in a week&nbsp;&mdash;&nbsp;on 23 June, time stays the same.")]
   [:p
    "Join a simultaneous work session from your computer by opening "
    [:a {:href (str "http://sws.eeefff.org" session-room-url)
         :target "_blank"}
     "THIS LINK"]
    " with "
    [:a {:href "https://www.google.com/chrome/"
         :target "_blank"}
     "Google Chrome"]
    ". Then add "
    [:a {:href "https://chrome.google.com/webstore/detail/google-hangouts/nckgahadagoaajjgafhacjanaoiihapd"
         :target "_blank"}
     "Hangouts Chrome extension"]
    " and press Join the video chat"]

   [:p
    "To join a simultaneous work session from your smartphone or tablet install the Hangouts app. For that open "
    [:a {:href (str "http://sws.eeefff.org" session-room-url)
         :target "_blank"}
     "THIS LINK"]
    " with "
    [:a {:href "https://www.google.com/chrome/"
         :target "_blank"}
     "Google Chrome"]
    " and join the call after installing Hangouts."]
   ;;
   ;; RUSSIAN
   ;;
   [:p
    "РУС"]
   [:p
    "Присоединиться можно по "
    [:a {:href (str "http://sws.eeefff.org" session-room-url)
         :target "_blank"}
     "ссылке"]
    "."]
   [:p
    "В одной сессии может принимать участие до 10 человек."]
   [:p
    "Представьте, что это союз нематериальных работников, виртуальное пространство совместной работы. Не отвлекайте друг друга, можете наблюдать, как кто-то другой занимается своим когнитивным трудом.
"]
   [:p
    "Можешь присутствовать на сессии своим изображением, можешь расшаривать экран со своей работой (кнопка «Показать экран» в меню слева, там изображен зеленый компьютер). Звук оставляешь включенным.
"]
   ;;
   ;; session description
   ;;
   [:p
    (unescapeEntities "В третьей сессии мы концентрируемся на бэкапах, точнее на том, что потеряется безвозвратно, если вдруг сломается компьютер или планшет или украдут телефон. Чтобы понять, чем именно вам заняться, проведите несколько минут, вспоминая те данные, которые вы безвозвратно потеряли. Это могут быть тексты, которые однажды пропали с рабочего стола,  а их копий не было. Монтаж видео, который вы делали целый день и который потерялся, когда монтажная программа рухнула под вечер, а файл вы не сохраняли. Отформатированная флешка с фотографиями со съемки, которые вы никуда не скопировали. Или неработающий сайт из-за потерявшихся данных во время сбоя на хостинге.")]
   [:p
    (unescapeEntities "Продолжительность сессии &mdash; 1 час, с 13.30 до 14.30 по Хельсинки и Москве.")]
   [:p
    (unescapeEntities "Следующая сессия будет через неделю &mdash; 16 июня в это же время.")]
   [:p
    "Если ты присоединяешься к сессии при помощи компьютера. Открываешь в "
    [:a {:href "https://www.google.com/chrome/"
         :target "_blank"}
     "Google Chrome"]
    " "
    [:a {:href (str "http://sws.eeefff.org" session-room-url)
         :target "_blank"}
     "ССЫЛКУ"]
    ". Устанавливаешь расширение "
    [:a {:href "https://chrome.google.com/webstore/detail/google-hangouts/nckgahadagoaajjgafhacjanaoiihapd"
         :target "_blank"}
     "Hangouts для Chrome"]
    ". Нажимаешь Присоединиться к видео звонку."]

   [:p
    "Если присоединяешься при помощи телефона или планшета, устанавливаешь приложение Hangouts.  Открываешь в "
    [:a {:href "https://www.google.com/chrome/"
         :target "_blank"}
     "Google Chrome"]
    " "
    [:a {:href (str "http://sws.eeefff.org" session-room-url)
         :target "_blank"}
     "ССЫЛКУ"]
    " и присоединяешься к видео звонку после установки приложения."]
   ])

;;;
;;;
;;; SCHEDULE VIEW
;;;
;;;
(defn schedule-view [header]
  [:div.details.schedule
   #_[:hr]
   [:h4.time-and-place
    [:a {:href "http://sws.eeefff.org/session-four-room"
         :target "_blank"}
     (unescapeEntities "Session&nbsp;#3")]
    (gstring/unescapeEntities ": 16&nbsp;June, 13:30&nbsp;&mdash;&nbsp;14:30&nbsp;Helsinki&nbsp;and&nbsp;Moscow&nbsp;time")]
   #_[:hr]
   #_[:h4
      (gstring/unescapeEntities "Session&nbsp;#2: 2&nbsp;June, 13:30&nbsp;&mdash;&nbsp;14:30&nbsp;Helsinki&nbsp;time")]
   #_[:hr]
   [instructions "/session-four-room"]
   #_[:hr]])

;;;
;;;
;;; GENERIC DOCUMENTATION VIEW
;;;
;;;
(defn documentation-view [header]
  [:div.details.documentatation
   [:div.responsive-embed.widescreen
    [:iframe
             {:width 560
              :height 315
              ;; vnd.youtube://
              ;; :src "vnd.youtube://www.youtube.com/watch?v=Qar_jUm7yhQ?autoplay=1"
              :src (str "https://www.youtube.com/embed/" (-> header :details :youtube-id) "?autoplay=1&loop=1&controls=1")
              :frame-border 0
              :auto-play 1
              :loop 1
              :controls 1
              ;; :start 1320
              ;; :end 1340
              ;; :end 3900
              :allow-full-screen true}]]])

;;;
;;;
;;; SESSION 1 DOCUMENTATION VIEW
;;;
;;;
;; (defn session-one-view [header]
;;   [documentation-view header])

;;;
;;;
;;; DESCRIPTION VIEW
;;;
;;;
(defn description-view [header]
  [:h4.details.description
   [:p "In a series of simultaneous work sessions, we invite you to
                                            join a work activity for
                                            an hour space of time."]

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
                union of non-material workers."]])

;;;
;;;
;;; HEADERS VIEW
;;;
;;;
(defn headers-view []
  (let [headers (subscribe [:headers/all])
        splash-screen-state (subscribe [:ui.splash-screen/state])]
    (fn []
      [:div.index
       (map (fn [header h-id]
              (with-meta
                [:div.header-container
                 ;; {:class (if (= @splash-screen-state :disabled)
                 ;;           ""
                 ;;           "hidden")}

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
                     [schedule-view header]

                     ;;
                     ;; session one
                     ;;
                     :session-one
                     [documentation-view header]

                     ;;
                     ;; session two
                     ;;
                     :session-two
                     [documentation-view header]


                     ;;
                     ;; description
                     ;;
                     :description
                     [description-view header]

                     ;;
                     ;; missing
                     ;;
                     [schedule-view header]))]
                {:key h-id}))
            @headers
            (range (count @headers)))])))
