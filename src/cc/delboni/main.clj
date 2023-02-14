(ns cc.delboni.main
  (:require [cc.delboni.deps :as deps]
            [clojure.tools.gitlibs :as gitlibs]
            [org.corfield.new :as new])
  (:gen-class))

(defn load-git [{:keys [git template]}]
  (deps/add-libs
   {(symbol template) {:git/url (str git)
                       :sha (gitlibs/commit-sha (str git) "HEAD")}}))

(defn create
  "Exec function to create a new project from a template.
  `:git` -- a symbol (or string) git url to fetch the template,
  `:template` -- a symbol (or string) identifying the template,
  `:name` -- a symbol (or string) identifying the project name,
  `:target-dir` -- optional string identifying the directory to
      create the new project in,
  `:overwrite` -- whether to overwrite an existing directory or,
      for `:delete`, to delete it first; if `:overwrite` is `nil`
      or `false`, an existing directory will not be overwritten."
  [{:keys [git] :as opts}]
  (when git
    (println "loading template from git")
    (load-git opts))
  (println "creating new project from template")
  (new/create opts))

(defn repo->url [base-url extension repo]
  (str base-url repo extension))

(def repo->github-url (partial repo->url "https://github.com/" ".git"))
(def repo->gitlab-url (partial repo->url "https://gitlab.com/" ".git"))
(def repo->bitbucket-url (partial repo->url "https://bitbucket.org/" ".git"))
(def repo->sourcehut-url (partial repo->url "https://git.sr.ht/~" ""))
(def repo->codeberg-url (partial repo->url "https://codeberg.org/" ".git"))

(defn input->opts
  [repo->url-fn repo {:keys [template] :as opts}]
  (assoc opts
         :git (repo->url-fn repo)
         :template (or template repo)))

(defn new [opts]
  (cond
    (:gh opts) (create (input->opts repo->github-url (:gh opts) opts))
    (:gl opts) (create (input->opts repo->gitlab-url (:gl opts) opts))
    (:bb opts) (create (input->opts repo->bitbucket-url (:bb opts) opts))
    (:sh opts) (create (input->opts repo->sourcehut-url (:sh opts) opts))
    (:cb opts) (create (input->opts repo->codeberg-url (:cb opts) opts))
    (:git opts) (create opts)
    :else (new/create opts)))

(comment
  (new
   {:gh         'rafaeldelboni/helix-scratch
    :template   'cc.delboni/helix-scratch
    :name       'cc.delboni/test-gh-helix
    :target-dir "new-out2"}))
