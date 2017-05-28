(ns work-sessions.db
  (:require [cljs.spec :as s]
            [work-sessions.pages :as pages]))

;;;
;;;
;;; CONSTANTS
;;;
;;;
;;;
;;; details attrs
;;;
(def details-data
  [{:type :description
    :route-key :about}
   {:type :schedule
    :route-key :schedule}
   {:type :session-one
    :route-key :session-one}])


;;;
;;;
;;; SPECS
;;;
;;;

;;;
;;; header
;;;
(s/def ::text string?)
(s/def ::details-visible? boolean?)
(s/def ::speed integer?)
(s/def ::reverse? boolean?)
(s/def ::angle integer?)
(s/def ::angled? boolean?)
(s/def ::hovered? boolean?)
(s/def ::link string?)
(s/def ::link-text string?)
(s/def ::route keyword?)

(s/def ::type #(apply hash-set
                     (map :type
                          details-data)))

;;;
;;; db
;;;
(s/def ::site-name string?)
(s/def ::is-proxy-viewer-docs-visible? boolean?)
(s/def ::splash-screen-sate #{:disabled
                              :enabled
                              :enabling
                              :disabling})

;;;
;;; structs
;;;
(s/def ::details (s/keys :req-un [::route
                                  ::link
                                  ::link-text
                                  ::type]))

(s/def ::header (s/keys :req-un [::text
                                 ::details-visible?
                                 ::speed
                                 ::reverse?
                                 ::angle
                                 ::angled?
                                 ::hovered?
                                 ::details]))

;;;
;;; colls
;;;
(s/def ::headers (s/coll-of ::header))

;;;
;;; DB SPEC
;;;
(s/def ::db (s/keys :req-un [::headers
                             ::site-name
                             ::is-proxy-viewer-docs-visible?
                             ::splash-screen-state]))

;;;
;;;
;;; default db attrs
;;;
;;;

(defn random-details []
  (let [details-d (rand-nth details-data)
        details-type (:type details-d)
        page-data #(->> pages/page-defs
                        (filter (fn [x] (= (:route-key details-d) (:key x))))
                        (first)
                        %)]
    {:type details-type
     :route (page-data :key)
     :link (page-data :route)
     :link-text (page-data :human-readable)}))
;;;
;;;
;;;
(defn default-header []
  {:text ""
   :details-visible? false
   :speed (Math/ceil (+ 30 (* 70 (rand))))
   :reverse? (if (> 0.5 (rand)) true false)
   :angle (Math/ceil (+ -10 (* 40 (rand))))
   :angled? false
   :hovered? false
   :details (random-details)})

;;;
;;; default db
;;;
(defn default-db []
  {:site-name "Simultaneous Work Sessions"
   :is-proxy-viewer-docs-visible? false
   :splash-screen-state :enabled
   :headers (repeatedly 10 default-header)})


;;;
;;;
;;; HEADER FUNCS
;;;
;;;

;;;
;;; model related
;;;


;;;
;;; modifiers
;;;
(defn get-header [db h-id]
  (-> db
      :headers
      (vec)
      (get h-id)))

(defn update-header [db h-id f]
  (update db :headers (fn [hs]
                        (assoc (vec hs) h-id (f (get (vec hs) h-id))))))

(defn update-headers [db f]
  (update db :headers (fn [hs] (map #(f %)
                                    hs))))

(defn update-headers-when [db when-f f]
  (update-headers db (fn [h]
                       (if (when-f h)
                         (f h)
                         h))))
