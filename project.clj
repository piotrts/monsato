(defproject monsato "0.1.0-SNAPSHOT"
  :description "A lightweight MutationObserver wrapper for ClojureScript"
  :url "http://github.com/piotrts/monsato"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [org.clojure/core.async "0.2.395"]]
  :plugins [[lein-cljsbuild "1.1.4" :exclusions [[org.clojure/clojure]]]]
  :prep-tasks ["compile" ["cljsbuild" "once"]]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :cljsbuild
    {:builds 
      [{:id "dev"
        :source-paths ["src"]
        :jar true
        :compiler
          {:main monsato.core
           :output-to "resources/public/js/compiled/monsato.js"
           :output-dir "resources/public/js/compiled/out"
           :optimizations :none
           :source-map true
           :pretty-print true}}
      {:id "min"
       :source-paths ["src"]
       :compiler
         {:output-to "resources/public/js/compiled/monsato.js"
          :optimizations :advanced
          :pretty-print false}}]}
  :source-paths ["src"])
