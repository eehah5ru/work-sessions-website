(ns work-sessions.handlers
  (:require
   [re-frame.core :refer [reg-event-db reg-event-fx path trim-v after debug reg-fx console dispatch]]
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]


   [work-sessions.db :as db]
   [work-sessions.pages :as pages]

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
                     {:ms 1500 :dispatch [:ui.header/enable-angles]}
                     {:ms 2000 :dispatch [:proxy-viewer.docs/setup-watchdog]}]}))

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
   (let [h-type (-> db
                    (db/get-header header-id)
                    :details
                    :type)
         test-f #(= (-> %
                        :details
                        :type)
                    h-type)
         modifier-f #(update %1 :details-visible? not)]
     {:db (-> db
              #_(db/update-headers-when test-f
                                      #(update % :details-visible? not))
              #_(db/update-headers-when #(not (test-f %))
                                        #(assoc % :details-visible? false))
              (db/update-header header-id #(update % :details-visible? not))
              #_(db/update-header header-id #(update % :hovered? not)))
      ;; :dispatch [:ui.page/change (-> db
      ;;                                (db/get-header header-id)
      ;;                                :details
      ;;                                :route)]
      })))

(reg-event-db
 :ui.header/hover

 (interceptors)

 (fn [db [h-id]]
   (console :log :ui.header/hover h-id (not (:hovered? (db/get-header db h-id))))
   (db/update-header db h-id #(update % :hovered? not))
   #_(db/update-header h-id #(assoc % :text (if-not (:hovered? %)
                                              (:site-name db)
                                              "aaa")))))

;;;
;;;
;;; PROXY VIEWER
;;;
;;;
(reg-event-db
 :proxy-viewer.docs/set-visible

 (interceptors)

 (fn [db [is-visible]]
   (assoc db :is-proxy-viewer-docs-visible? is-visible)))


(reg-event-fx
 :proxy-viewer.docs/setup-watchdog

 (interceptors-fx :spec false)

 (fn [{:keys [db]}]
   {:dispatch-later [{:ms 5000
                      :dispatch [:proxy-viewer.docs/setup-watchdog]}
                     {:ms 100
                      :dispatch [:proxy-viewer.docs/check-visibility]}]}))


(reg-event-fx
 :proxy-viewer.docs/check-visibility

 (interceptors-fx :spec false)

 (fn [{:keys [db]}]
   {:http-xhrio {:method :get
                 :uri "/proxy-viewer/is-docs-visible"
                 :response-format (ajax/raw-response-format)
                 :timout 6000
                 :on-success [:proxy-viewer.docs/set-visible true]
                 :on-failure [:proxy-viewer.docs/set-visible false]}}))
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
