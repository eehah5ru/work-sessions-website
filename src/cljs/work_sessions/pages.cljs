(ns work-sessions.pages
  (:require
   [bidi.bidi :as bidi]

   ;; [cljs-react-material-ui.reagent :as ui]
   ;; [cljs-react-material-ui.icons :as icons]

   [work-sessions.utils :as u]
   ;;
   ;; pages
   ;;
   [work-sessions.components.home-page :refer [home-page]]
   [work-sessions.components.about-page :refer [about-page]]
   ;; [work-sessions.components.schedule-page :refer [schedule-page]]
   ;; [work-sessions.components.proxy-viewer-page :refer [proxy-viewer-page]]
   [work-sessions.components.session-one-page :refer [session-one-page]]
   [work-sessions.components.session-two-page :refer [session-two-page]]
   [work-sessions.components.session-three-page :refer [session-three-page]]
   [work-sessions.components.session-four-page :refer [session-four-page]]


   [work-sessions.components.session-not-found-page :refer [session-not-found-page]]

   ))



(def page-defs
  [
   ;; home
   {:key :home
    :route true
    :human-readable "Home"
    :page-view home-page
    }
   {:key :about
    :route "about"
    :human-readable "About"
    :page-view about-page}
   ;;
   ;; sessions
   ;;
   {:key :session-one
    :route "session-one"
    :human-readable "Session #1"
    :page-view session-one-page}

   {:key :session-two
    :route "session-two"
    :human-readable "Session #2"
    :page-view session-two-page}

   {:key :session-three
    :route "session-three"
    :human-readable "Session #3"
    :page-view session-three-page}

   {:key :session-four
    :route "session-four"
    :human-readable "Session #4"
    :page-view session-four-page}

   ;;
   ;; not found
   ;;
   {:key :session-not-found
    :route "session-not-found"
    :human-readable "Session not found"
    :page-view session-not-found-page}

   ])

;;;
;;;
;;; bidi/pushy routes
;;;
;;;
(def routes
  ["/" (let [pages (filter #(not= (:route %) true) page-defs)
             default (filter #(= (:route %) true) page-defs)]
         (map #(vector (:route %) (:key %)) (concat pages default)))])

;;;
;;;
;;; pages with components to render
;;;
;;;
(def pages
  (reduce merge
          {}
          (map #(hash-map (:key %) (:page-view %)) page-defs)))

;;;
;;;
;;; drawer menu items
;;;
;;;
;; (defn menu-pages [& {:keys [read-only-app?]}]
;;   (let [ps (cond->> page-defs
;;              true
;;              (filter (complement :hidden-from-menu?))
;;              read-only-app?
;;              (filter (complement :only-full-app?)))]
;;     (map #(vector (:key %)
;;                   (:human-readable %)
;;                   (:menu-icon %))
;;          ps)))

;;;
;;; human readable for page
;;;
(defn human-readable [page-key]
  (->> page-defs
   (filter #(= (:key %) page-key))
   first
   :human-readable))

;;;
;;;
;;; UTILS
;;;
;;;
(def path-for (partial bidi/path-for routes))
