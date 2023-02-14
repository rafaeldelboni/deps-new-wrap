(ns cc.delboni.main-test
  (:require [clojure.test :refer [deftest is testing]]
            [cc.delboni.main :as main]))

(deftest repo->url-test
  (testing "should build the git urls"
    (is (= "base/owner/repo.ext"
           (main/repo->url "base/" ".ext" "owner/repo")))

    (is (= "base/owner/repo"
           (main/repo->url "base/" "" "owner/repo")))))

(deftest repo->any-url-test
  (testing "should build the git urls"
    (is (= "https://github.com/owner/repo.git"
           (main/repo->github-url "owner/repo")))

    (is (= "https://gitlab.com/owner/repo.git"
           (main/repo->gitlab-url "owner/repo")))

    (is (= "https://bitbucket.org/owner/repo.git"
           (main/repo->bitbucket-url "owner/repo")))

    (is (= "https://git.sr.ht/~owner/repo"
           (main/repo->sourcehut-url "owner/repo")))

    (is (= "https://codeberg.org/owner/repo.git"
           (main/repo->codeberg-url "owner/repo")))))

(deftest input->opts-test
  (testing "should build the git urls"
    (is (= {:git "https://github.com/owner/repo.git"
            :repo "owner/repo"
            :template "owner/repo"}
           (main/input->opts main/repo->github-url "owner/repo" {})))
    (is (= {:git "https://github.com/owner/repo.git"
            :repo "owner/repo"
            :template "ns/name"}
           (main/input->opts main/repo->github-url "owner/repo" {:template "ns/name"})))))
