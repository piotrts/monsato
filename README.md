# Monsato

**A lightweight MutationObserver wrapper for ClojureScript**

Work in progress. Current version: 0.1.0.

## Installation

Add the following dependency to your `project.clj`:

```clojure
[monsato "0.1.1"]
```

## Example usage

```clojure
(require '[monsato.core :as m]
         '[goog.dom :as gdom])

(def obs (m/observer))

(m/observe obs (gdom/getElement "watchme") #(prn %))
  ```
`observe` also accepts a core.async channel as its last parameter:

```clojure
(let [obs (m/observer)
      ch (chan)]
  (go-loop []
    (prn (<! ch))
    (recur))
  (m/observe obs (gdom/getElement "watchme") ch))
```
