(defproject dijkstra.clj "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.6.0"],
                 [org.clojure/data.priority-map "0.0.7"]]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :aot [dijkstra.clj.core]
  :main dijkstra.clj.core)
