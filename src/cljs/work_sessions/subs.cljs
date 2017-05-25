(ns work-sessions.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]
            [cemerick.url :as url]

            [work-sessions.db :as db]

            [work-sessions.pages :as pages]
            ))

(reg-sub
 :headers/all

 (fn [db]
   (:headers db [])))

;;;
;;;
;;; pages
;;;
;;;
(reg-sub
 :ui.page/current
 (fn [db]
   (:current-page db)))

(reg-sub
 :ui.page.current/name
 :<- [:ui.page/current]
 (fn [current-page _]
   (pages/human-readable (:handler current-page))))
