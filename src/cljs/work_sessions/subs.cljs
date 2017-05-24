(ns work-sessions.subs
  (:require [re-frame.core :refer [reg-sub subscribe]]
            [cemerick.url :as url]

            [work-sessions.db :as db]))

(reg-sub
 :headers/all

 (fn [db]
   (:headers db [])))
