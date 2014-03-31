(ns dogeapi-clojure.core
  (:require [clj-http :as http]))

(defn- fn-with-stuff [fn-map]
  ; takes in map in format {:fn-name '([fn-args] fn-body)} and generates code for those functions
  (cond

   (nil? (resolve 'api-key))
    (doseq [f fn-map]
      (eval `(defn ~(symbol (apply str (rest (str (nth f 0))))) [& ~(quote stuff)] (println "set api key first with (set-api-key [key])")))
    )

    (nil? (resolve 'base-url))
     (doseq [f fn-map]
      (eval `(defn ~(symbol (apply str (rest (str (nth f 0))))) [& ~(quote stuff)] (println "set version first with (set-endpoint-version [version])")))
    )

    :else
    (if (and (= fn-map fns-v2) (= version 1))
      (doseq [f fn-map]
        (eval `(defn ~(symbol (apply str (rest (str (nth f 0))))) [& ~(quote stuff)] (println "this functionality only available in v2 api.")))
      )

      (doseq [f fn-map]

        (eval `(defn ~(symbol (apply str (rest (str (nth f 0)))))
           ~(nth (nth f 1) 0)
           ~(nth (nth f 1) 1)))
      )
    )
  )
)

(defn set-api-key [key]
  ; takes a string based on your API key
  (if (not (= (class key) java.lang.String)) (println "enter a valid string for API key")
      (def api-key key))
)


(defn set-endpoint-version [v]
  ; takes an integer 1 or 2 so you can use either v1 or v2
  (cond
    (= 2 v) (do (def ^{:private true} base-url "https://www.dogeapi.com/wow/v2/")
                (def ^{:private true} version 2)
                (println "Using API v2")
                (fn-with-stuff fns)
                (fn-with-stuff fns-v2))
    (= 1 v) (do (def ^{:private true} base-url "https://www.dogeapi.com/wow/")
                (def ^{:private true} version 1)
                (println "Using API v1")
                (fn-with-stuff fns)
                (fn-with-stuff fns-v2))
    :else (println "use either 1 or 2")
  )
)

(defn get-endpoint-version []
  (str "v" version))

; damn you can totally tell I do Javascript development LOL
(def ^{:private true} fns {

:get-balance '()
:withdraw '()
:get-new-address '()
:get-my-addresses '()
:get-address-received '()
:get-address-by-label '()
:get-difficulty '()
:get-current-block '()
:get-current-price '()

})

(def ^{:private true} fns-v2 {

:create-user '()
:get-user-address '()
:get-user-balance '()
:withdraw-from-user '()
:move-to-user '()
:get-users '()
:get-network-hashrate '()
:get-info '()

})

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!")
)
