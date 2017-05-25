(ns work-sessions.handlers
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx path trim-v after debug reg-fx console dispatch]]

   [work-sessions.db :as db]

   [work-sessions.handlers.interceptors :refer [interceptors
                                                interceptors-fx]]))

;;;
;;; initial
;;;
(reg-event-fx
 :initialize

 (interceptors-fx :spec true)

 (fn [_ _]
   (console :log :initialize)

   ;; (console :log :marquee3k :enabled)
   {:db (-> (db/default-db)
            (as-> db (db/update-headers db
                                     (fn [h]
                                       (assoc h :text (:site-name db))))))
    :dispatch-later [{:ms 500 :dispatch [:ui.marquee/setup]}
                     {:ms 1500 :dispatch [:ui.header/enable-angles]}]}))

;;;
;;; marquee on headers
;;;
(reg-event-fx
 :ui.marquee/setup

 (interceptors-fx :spec false)

 (fn [_ _]
   (console :log :marquee3k :setting-up)
   (js/Marquee3k)
   ;; do nothing
   {}))


;;;
;;; headers
;;;
(reg-event-db
 :ui.header/enable-angles

 (interceptors)

 (fn [db]
   (db/update-headers db #(update % :angled? (fn [] true)))))


(reg-event-fx
 :ui.header/click

 (interceptors-fx :spec true)

 (fn [{:keys [db]} [header-id]]
   ;; (throw :aaa)
   (console :log :header-clicked header-id)
   {:db (db/update-header db header-id #(update %1 :details-visible? not))}))

(reg-event-db
 :ui.header/hover

 (fn [db [_ h-id]]
   ;; (console :log :hovered h-id)
   (-> db
       (db/update-header h-id #(update % :hovered? not))
       #_(db/update-header h-id #(assoc % :text (if-not (:hovered? %)
                                                (:site-name db)
                                                "aaa"))))))

;;;
;;;
;;; PAGES
;;;
;;;

(reg-event-fx
 :ui.page/set-current
 (interceptors-fx :spec true)

 (fn [{:keys [db]} [match]]
   {:db (assoc db
               :current-page match)}))


;;;
;;; change location
;;;
(reg-event-fx
 :ui.page/change
 (interceptors-fx :spec false)

 (fn [_ [& route-params]]
   (aset js/window "location" (apply pages/path-for route-params))
   {}))


;; (reg-event-db
;;  :ui.header/hover

;;  (interceptors)

;;  (fn [db [h-id]]
;;    (db/update-header db h-id (fn [h]
;;                             (assoc h :text "aaa")))))
