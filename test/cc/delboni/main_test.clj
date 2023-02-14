(ns cc.delboni.main-test
  (:require [clojure.test :refer [deftest is testing]]
            [cc.delboni.main :as main]))

(deftest repo->url-test
  (testing "should build the git urls"
    (is (= "https://github.com/owner/repo.git"
           (main/repo->github-url {:template "owner/repo"})))
    (is (= "https://github.com/owner/repo.git"
           (main/repo->github-url {:repo "owner/repo" :template "ns/name"})))

    (is (= "https://gitlab.com/owner/repo.git"
           (main/repo->gitlab-url {:template "owner/repo"})))
    (is (= "https://gitlab.com/owner/repo.git"
           (main/repo->gitlab-url {:repo "owner/repo" :template "ns/name"})))

    (is (= "https://bitbucket.org/owner/repo.git"
           (main/repo->bitbucket-url {:template "owner/repo"})))
    (is (= "https://bitbucket.org/owner/repo.git"
           (main/repo->bitbucket-url {:repo "owner/repo" :template "ns/name"})))

    (is (= "https://git.sr.ht/~owner/repo"
           (main/repo->sourcehut-url {:template "owner/repo"})))
    (is (= "https://git.sr.ht/~owner/repo"
           (main/repo->sourcehut-url {:repo "owner/repo" :template "ns/name"})))

    (is (= "https://codeberg.org/owner/repo.git"
           (main/repo->codeberg-url {:template "owner/repo"})))
    (is (= "https://codeberg.org/owner/repo.git"
           (main/repo->codeberg-url {:repo "owner/repo" :template "ns/name"})))))
