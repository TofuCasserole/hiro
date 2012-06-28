(ns hiro.dialog
  (:import (de.saar.coli.salsa.reiter.framenet
             Frame
             FrameNet
             FNDatabaseReader15)
           (java.io File)))

(defn get-frame-db [uri]
  (doto (new FrameNet)
    (.readData (new FNDatabaseReader15 (new File uri) true))))

(defstruct clause :frame :values)

(defstruct agent :name :beliefs :goals :plans :emotion)

(defstruct event :clause :time)

(defstruct environment :agents :events)

(defn create-env [agents events]
  (struct-map environment
              :agents (loop [agent-map {}
                             new-agents agents]
                        (let [agent-vals (first new-agents)]
                          (if agent-vals
                            (recur (let [agn (eval `(struct-map agent ~@agent-vals))]
                                     (assoc agent-map (get agn :name) agn))
                                   (rest new-agents))
                            agent-map)))
              :events (loop [event-vec []
                             new-events events]
                        (let [event-vals (first new-events)]
                          (if event-vals
                            (recur (assoc event-vec (count event-vec) (eval `(struct-map event :time ~(count event-vec) ~@event-vals)))
                                   (rest new-events))
                            event-vec)))))
  

(defn generate-dialog-tree [state pc npc]
  (let [frame-net (get-frame-db ".\\fndata-1.5")]
    nil))
