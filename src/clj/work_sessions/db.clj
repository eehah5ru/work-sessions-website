(ns work-sessions.db
  (:require [clojure.java.io :as io]
            [ring.logger.onelog :as logger.onelog]

            ))

(defn- default-db []
  {:is-docs-visible false})

(defn create []
  (atom (default-db)))
