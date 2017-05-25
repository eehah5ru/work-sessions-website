(ns work-sessions.pages
  (:require
   [bidi.bidi :as bidi]

   ;; [cljs-react-material-ui.reagent :as ui]
   ;; [cljs-react-material-ui.icons :as icons]

   [work-sessions.utils :as u]
   ;;
   ;; pages
   ;;
   ;; [childrensfutures-trade.components.home-page :refer [home-page]]
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
   {:key :Home
    :route true
    :human-readable "Home"
    :page-view "work-sessions.components.home-page/home-page"
    }
   {:key :proxy-viewer
    :route "proxy-viewer"
    :human-readable "Proxy Viewer"
    :page-view "work-sessions.components.proxy-viewer-page/proxy-viewer-page"}
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
          (map #(hash-map (:key %) (fn [] (u/invoke (:page-view %)))) page-defs)))

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
