(ns monsato.core
  (:require [cljs.core.async :refer [put! <!]]
            [clojure.core.async.impl.channels :refer [ManyToManyChannel]]
            [goog.dom :as dom]))

(def ^:private default-mutation-observer-opts
  {:attributes true
   :attributeOldValue true
   :characterDataOldValue true
   :characterData true
   :childList true
   :subtree true})

(defprotocol IObservable
  (observe! [this node afn] [this node afn mo-opts])
  (neglect! [this] [this node]))

(deftype Observer [^:mutable mo ^:mutable nfns]
  IObservable
  (observe! [_ node afn]
    (observe! _ node afn default-mutation-observer-opts))

  (observe! [_ node afn mo-opts]
    (set! nfns (assoc nfns node afn))
    (.observe mo node (clj->js mo-opts)))

  (neglect! [_]
    (set! nfns {})
    (.disconnect mo))

  (neglect! [_ node]
    (set! nfns (dissoc nfns node))
    (when-not (seq nfns)
      (.disconnect mo))))

(defn observer []
  (let [obs (->Observer nil {})]
    (set! (.-mo obs)
          (js/MutationObserver.
            (fn [ms _]
              (doseq [mr ms]
                (when-let [afn (or (get (.-nfns obs) (.-target mr))
                                   (get (.-nfns obs) (.. mr -target -parentNode)))]
                  (if (instance? ManyToManyChannel afn)
                    (put! afn mr)
                    (afn mr)))))))
    obs))

