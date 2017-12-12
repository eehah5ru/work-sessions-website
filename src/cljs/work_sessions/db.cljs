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
   ;;
   ;; sessions
   ;;
   {:type :session-one
    :route-key :session-one
    :youtube-id "zYTfUtroTJI"}

   {:type :session-two
    :route-key :session-two
    :youtube-id "wd49EhHutPk"}

   {:type :session-three
    :route-key :session-three
    :youtube-id "-DcI9vNPMJA"}

   {:type :session-four
    :route-key :session-four
    :youtube-id "pr5WhEkJsIQ"}
   ])


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
;;;
;;; make db-headers-details item from generic details-data item
;;;
(defn mk-details [details-d]
  (let [details-type (:type details-d)
        page-data #(->> pages/page-defs
                        (filter (fn [x] (= (:route-key details-d) (:key x))))
                        (first)
                        %)]
    (merge details-d
           {:route (page-data :key)
            :link (page-data :route)
            :link-text (page-data :human-readable)})))

;;;
;;; make random details
;;;
(defn random-details []
  (mk-details (rand-nth details-data)))

;;;
;;;
;;;
(defn default-header [details]
  {:text ""
   :details-visible? false
   :speed (Math/ceil (+ 30 (* 70 (rand))))
   :reverse? (if (> 0.5 (rand)) true false)
   :angle (Math/ceil (+ -10 (* 40 (rand))))
   :angled? false
   :hovered? false
   :details details})

;;;
;;; default db
;;;
(defn default-db []
  {:site-name "Simultaneous Work Sessions"
   :is-proxy-viewer-docs-visible? false
   :splash-screen-state :enabled
   :headers (map #(-> %
                      mk-details
                      default-header)
                 details-data)})


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
