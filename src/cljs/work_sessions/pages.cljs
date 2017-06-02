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
   [work-sessions.components.schedule-page :refer [schedule-page]]
   [work-sessions.components.proxy-viewer-page :refer [proxy-viewer-page]]
   [work-sessions.components.session-one-page :refer [session-one-page]]

   [work-sessions.components.session-not-found-page :refer [session-not-found-page]]
   ;; [childrensfutures-trade.components.my-goals-page :refer [my-goals-page]]
   ;; [childrensfutures-trade.components.pulse-page :refer [pulse-page]]
   ;; [childrensfutures-trade.components.how-to-play-page :refer [how-to-play-page]]
   ;; [childrensfutures-trade.components.about-page :refer [about-page]]
   ;; [childrensfutures-trade.components.view-goal-page :refer [view-goal-page]]
   ;; [childrensfutures-trade.components.my-events-page :refer [my-events-page]]
   ;; [childrensfutures-trade.components.latest-events-page :refer [latest-events-page]]

   ))



(def page-defs
  [
   ;; home
   {:key :home
    :route true
    :human-readable "Home"
    :page-view home-page
    }
   {:key :proxy-viewer
    :route "proxy-viewer"
    :human-readable "Proxy Viewer"
    :page-view proxy-viewer-page}
   {:key :about
    :route "about"
    :human-readable "About"
    :page-view about-page}
   {:key :schedule
    :route "schedule"
    :human-readable "Schedule"
    :page-view schedule-page}
   {:key :session-one
    :route "session-one"
    :human-readable "Session #1"
    :page-view session-one-page}
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
