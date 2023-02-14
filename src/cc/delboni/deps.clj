(ns cc.delboni.deps
  "Based on code from this branch: https://github.com/clojure/tools.deps/tree/add-lib3"
  (:require [clojure.java.io :as jio]
            [clojure.set :as set]
            [clojure.string :as str]
            [clojure.tools.deps :as deps])
  (:import [clojure.lang DynamicClassLoader]
           [java.io File]))

(defn- include-dynamic-class-loader
  []
  (->> (Thread/currentThread)
       (.getContextClassLoader)
       (clojure.lang.DynamicClassLoader.)
       (.setContextClassLoader (Thread/currentThread))))

(defn- read-basis
  []
  (when-let [f (jio/file (System/getProperty "clojure.basis"))]
    (if (and f (.exists f))
      (deps/slurp-deps f)
      (throw (IllegalArgumentException. "No basis declared in clojure.basis system property")))))

(defn- add-loader-url
  "Add url string or URL to the highest level DynamicClassLoader url set."
  [url]
  (let [u (if (string? url) (java.net.URL. url) url)
        loader (loop [loader (.getContextClassLoader (Thread/currentThread))]
                 (let [parent (.getParent loader)]
                   (if (instance? DynamicClassLoader parent)
                     (recur parent)
                     loader)))]

    (when-not (instance? DynamicClassLoader loader)
      (include-dynamic-class-loader))

    (.addURL ^DynamicClassLoader loader u)))

(defn set-java-class-path [paths]
  (let [new-paths (str (System/getProperty "java.class.path") ":" (str/join ":" paths))]
    (System/setProperty "java.class.path" new-paths)))

(defn add-libs
  "Add map of lib to coords to the current runtime environment. All transitive
  dependencies will also be considered (in the context of the current set
  of loaded dependencies) and new transitive dependencies will also be
  loaded. Returns seq of all added libs or nil if couldn't be loaded.
  Note that for successful use, you must be in a REPL environment where a
  valid parent DynamicClassLoader can be found in which to add the new lib
  urls.
  Example:
   (add-libs '{org.clojure/core.memoize {:mvn/version \"0.7.1\"}})"
  [lib-coords]
  (let [{:keys [libs] :as initial-basis} (read-basis)]
    (if (empty? (set/difference (-> lib-coords keys set) (-> libs keys set)))
      nil
      (let [updated-deps (reduce-kv (fn [m k v] (assoc m k (dissoc v :dependents :paths))) lib-coords libs)
            updated-edn (merge (dissoc initial-basis :libs :classpath :deps) {:deps updated-deps})
            {updated-libs :libs} (deps/calc-basis updated-edn (select-keys initial-basis [:resolve-args :cp-args]))
            new-libs (select-keys updated-libs (set/difference (set (keys updated-libs)) (set (keys libs))))
            paths (mapcat :paths (vals new-libs))
            urls (->> paths (map jio/file) (map #(.toURL ^File %)))]
        (set-java-class-path paths)
        (include-dynamic-class-loader)
        (run! add-loader-url urls)
        (keys new-libs)))))
