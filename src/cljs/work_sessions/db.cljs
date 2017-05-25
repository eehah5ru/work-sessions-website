(ns work-sessions.db
  (:require [cljs.spec :as s]))

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

(s/def ::type #{:description
                :schedule
                :documentation})

;;;
;;; db
;;;
(s/def ::site-name string?)
(s/def ::is-proxy-viewer-docs-visible? boolean?)

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
                             ::is-proxy-viewer-docs-visible?]))

;;;
;;;
;;; default db attrs
;;;
;;;

(defn random-details []
  (let [details-type (let [i (rand)]
                       (cond
                         (< i 0.5)
                         :description

                         :else
                         :schedule))
        route (condp = details-type
                :description
                :about

                :schedule
                :schedule)

        link (condp = details-type
               :description
               "/about"

               :schedule
               "/schedule")
        link-text (condp = details-type
                    :description
                    "About"

                    :schedule
                    "Schedule")]
    {:type details-type
     :route route
     :link link
     :link-text link-text}))
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
