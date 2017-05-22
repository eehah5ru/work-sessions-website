(use '[clojure.java.shell :only [sh]])

(defproject work-sessions-website :lein-v

  :description "simultaneous work sessions website"
  :url "http://sws.eeefff.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]

                 ;;
                 ;; ui
                 ;;
                 [cljsjs/react-flexbox-grid "1.0.0-0"]
                 [reagent "0.6.2"]
                 [re-frame "0.9.2"]

                 ;;
                 ;; frontend utils
                 ;;
                 [day8.re-frame/http-fx "0.0.4"]
                 [madvas.re-frame/google-analytics-fx "0.1.0"]
                 [bidi "2.0.14"]
                 [bk/ring-gzip "0.1.1"]
                 [cljs-ajax "0.5.8"]
                 [com.andrewmcveigh/cljs-time "0.4.0"]

                 ;;
                 ;; misc
                 ;;
                 [environ "1.0.3"]
                 [http-kit "2.2.0"]
                 [medley "0.8.3"]
                 [print-foo-cljs "2.0.3"]
                 [ring-logger-onelog "0.7.6"]
                 [clj-logging-config "1.9.12"]
                 [ring/ring-core "1.6.0-beta5"]
                 [ring/ring-defaults "0.3.0-beta1"]
                 [ring/ring-devel "1.6.0-beta5"]
                 [com.cemerick/url "0.1.1"]
                 [com.cognitect/transit-cljs "0.8.239"]
                 [com.cognitect/transit-clj "0.8.297"]

                 ;;
                 ;; clojurescript
                 ;;
                 [org.clojure/clojurescript "1.9.229"]
                 ]

  :plugins [[lein-auto "0.1.2"]
            [lein-cljsbuild "1.1.4"]
            [lein-shell "0.5.0"]
            [lein-scss "0.3.0"]
            [deraen/lein-less4j "0.5.0"]
            [com.roomkey/lein-v "6.1.0"]
            [org.clojars.eehah5ru/lein-filegen-ng "0.0.1"]
            [org.clojars.eehah5ru/cljsbuild-extras "0.0.2"]]


  :main ^:skip-aot work-sessions-website.core

  :source-paths ["src/clj"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]

  :figwheel {:css-dirs ["resources/public/css"]
             :server-port 6877
             :ring-handler user/http-handler
             :server-logfile "log/figwheel.log"}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :release-tasks [["vcs" "assert-committed"]
                  ["v" "update"] ;; compute new version & tag it
                  ["vcs" "push"]]


  :target-path "target/%s"

  ;;
  ;;
  ;;
  :hooks [leiningen.scss]

  :scss {:builds
         {:dev {:source-dir "scss/"
                :dest-dir   "public/css/"
                :executable "sassc"
                :args       ["-m" "-I" "scss/" "-t" "nested"]}}}


  :profiles
  {
   ;;
   ;; local dev
   ;;
   :dev
   {:dependencies [[binaryage/devtools "0.8.2"]
                   [com.cemerick/piggieback "0.2.1"]
                   [figwheel-sidecar "0.5.10"]
                   [org.clojure/tools.nrepl "0.2.11"]]

    :plugins [[lein-figwheel "0.5.10"]]

    :source-paths ["env/dev"]
    ;;
    ;; generate index.html
    ;;
    :filegen-ng [{:data {:template ~(slurp "resources/public/index_tpl.html")
                         :version ~(fn [p] (:version p))}
                  :template-fn ~(fn [p d] (:template d))
                  :target "resources/public/index.html"}]

    ;;
    ;; build cljs
    ;;
    :cljsbuild {:builds [{:id "dev"
                          :source-paths ["src/cljs"]
                          :figwheel {:on-jsload "work-sessions-website.core/mount-root"}
                          :compiler {:main ^:skip-aot work-sessions-website.core
                                     :output-to "resources/public/js/compiled/app.js"
                                     :output-dir "resources/public/js/compiled/out"
                                     :asset-path "/js/compiled/out"
                                     :source-map-timestamp true
                                     :optimizations :none
                                     :closure-defines {goog.DEBUG true}
                                     :preloads [print.foo.preloads.devtools]}}]}

    }})
