(ns dogeapi-clojure.core
  (:require [clj-http :as http]))

(defn fn-with-stuff [fn-map]
  (if (or (nil? (resolve 'api-key)) (nil? (resolve 'base-url)))

    (doseq [f fn-map]
      (eval `(defn ~(symbol (apply str (rest (str (nth f 0))))) [& ~(quote stuff)] (println "set api key or version first")))
    )

    (doseq [f fn-map]

      (eval `(defn ~(symbol (apply str (rest (str (nth f 0)))))
         ~(nth (nth f 1) 0)
         ~(nth (nth f 1) 1)))
    )
  )
)

(defn set-api-key [key]
  (if (not (= (class key) java.lang.String)) (println "enter a valid string for API key")
      (def api-key key))
)


(defn get-endpoint-version [v]
  (cond
    (= 2 v) (do (def base-url "https://www.dogeapi.com/wow/v2/") (println "Using API v2") (fn-with-stuff fns))
    (= 1 v) (do (def base-url "https://www.dogeapi.com/wow/") (println "Using API v1") (fn-with-stuff fns))
    :else (println "use either 1 or 2")
  )
)



(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!")
)
