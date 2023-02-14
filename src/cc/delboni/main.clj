(ns cc.delboni.main
  (:require [cc.delboni.deps :as deps]
            [clojure.tools.gitlibs :as gitlibs]
            [org.corfield.new :as new])
  (:gen-class))

(defn load-git [git-url {:keys [template]}]
  (deps/add-libs
   {(symbol template) {:git/url git-url
                       :sha (gitlibs/commit-sha git-url "HEAD")}}))

(defn repo->url [base-url extension {:keys [repo template]}]
  (let [deps-name (symbol (or repo template))]
    (str base-url deps-name extension)))

(def repo->github-url (partial repo->url "https://github.com/" ".git"))
(def repo->gitlab-url (partial repo->url "https://gitlab.com/" ".git"))
(def repo->bitbucket-url (partial repo->url "https://bitbucket.org/" ".git"))
(def repo->sourcehut-url (partial repo->url "https://git.sr.ht/~" ""))
(def repo->codeberg-url (partial repo->url "https://codeberg.org/" ".git"))

(defn create
  "Exec function to create a new project from a template.
  `:repo` -- a symbol (or string) identifying the template is in github,
  `:template` -- a symbol (or string) identifying the template,
  `:name` -- a symbol (or string) identifying the project name,
  `:target-dir` -- optional string identifying the directory to
      create the new project in,
  `:overwrite` -- whether to overwrite an existing directory or,
      for `:delete`, to delete it first; if `:overwrite` is `nil`
      or `false`, an existing directory will not be overwritten."
  [git-url-fn opts]
  (println "loading template from git")
  (-> opts
      git-url-fn
      (load-git opts))
  (println "creating new project from template")
  (new/create (dissoc opts :repo)))

(defn git [{:keys [url] :as opts}]
  (create (fn [_] (str url)) (dissoc opts :url)))

(def gh (partial create repo->github-url))

(def gl (partial create repo->gitlab-url))

(def bb (partial create repo->bitbucket-url))

(def sh (partial create repo->sourcehut-url))

(def cb (partial create repo->codeberg-url))

(comment
  (gh
   {:repo       'rafaeldelboni/helix-scratch
    :template   'cc.delboni/helix-scratch
    :name       'cc.delboni/test-gh-helix
    :target-dir "new-out2"}))
