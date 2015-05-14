(ns dijkstra.clj.core
  (:require [clojure.data.priority-map :refer :all]
            [clojure.set :only [intersection]])
  (:gen-class))
  

(defn adjacent [G v] (get G v))

(defn adjacent-vertices [G v] (keys (G v)))

(defn init-distance-map [graph source]
  (if (nil? (first graph))
    nil
     (merge (if (= (key (first graph)) source)
                 {(key (first graph)) 0}
                 {(key (first graph)) 9999})
           (init-distance-map (rest graph) source))))

(def edges (get (first graph) 1))

(defn remove-edges-not-in-queue [queue edges]
  (apply dissoc edges 
         (clojure.set/difference 
           (into #{} (keys edges)) 
           (into #{} (keys queue)))))
                          
(defn relax [start edges queue]
      (if (empty? edges)
        queue
        ;; recursive call of relax passing next edge and updated queue
       (relax start (dissoc edges (get (first edges) 0)) 
              ;; we will change the value of the distance 
              ;; to the current edge by updating map
              (assoc queue (get (first edges) 0) 
                     ;; compare if new distance of current vertex 
                     ;; + distance to edge is less than what is in queue
                     (if (> (+ (get queue start) (get (first edges) 1)) (get queue (get (first edges) 0)))
                       ;; update queue key with same value
                       (get queue (get (first edges) 0))
                       ;; update with shorter distance 
                       (+ (get queue start) (get (first edges) 1)))))))



(defn dijkstra [graph queue]
  (if (= (count queue) 1)
    (first queue)
    (concat (first queue) 
            (dijkstra graph 
                      (dissoc 
                        (relax (key (first queue)) (remove-edges-not-in-queue queue (get graph (key (first queue)))) queue) 
                        (get (first queue) 0))))))

(def graph {:A {:B 10, :C 5 }, :B { :C 2, :D 1 }, :C { :B 3, :D 9, :E 2}, :D {:E 4}, :E {:A 7, :D 6}})

(def queue (into (priority-map) (init-distance-map graph :A)))

(defn -main
  []
  (dijkstra graph queue))
