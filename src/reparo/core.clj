(ns reparo.core
  (use clojure.pprint)
  (require [tentacles.pulls :as p]))

(def personal-access-token "write personal access token (https://github.com/settings/applications)")
(def commit-message "Merged by reparo")

(defn- get-pull-by-branch-name [pulls branch-name]
  (first (filter #(= branch-name (:ref (:head %))) pulls)))

(defn- merge-pull-request [pull]
  (let [user (:login (:user (:head pull)))
        repo (:name (:repo (:head pull)))
        number (:number pull)]
    (p/merge user repo number {:commit-message commit-message
                               :oauth-token personal-access-token})))

(defn -main [& args]
  "usage: reparo user repo branch-name"
  (let [branch-name (last args)
        pulls (apply p/pulls (drop-last args))
        pull (get-pull-by-branch-name pulls branch-name)]
    (merge-pull-request pull)))



















