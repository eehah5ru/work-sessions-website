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
(s/def ::details #{:description
                   :schedule
                   :documentation})

;;;
;;; db
;;;
(s/def ::site-name string?)
;;;
;;; structs
;;;
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
                             ::site-name]))

;;;
;;;
;;; default db attrs
;;;
;;;

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
   :details (let [i (rand)]
              (cond
                (< i 0.33)
                :description

                (and (> i 0.33)
                     (< i 0.66))
                :schedule

                :else
                :documentation))})

;;;
;;; default db
;;;
(defn default-db []
  {:site-name "Simultaneous Work Sessions"
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
(defn update-header [db h-id f]
  (update db :headers (fn [hs]
                        (assoc (vec hs) h-id (f (get (vec hs) h-id))))))

(defn update-headers [db f]
  (update db :headers (fn [hs] (map #(f %)
                                    hs))))
