(ns dogeapi-clojure.core
  (:require [clj-http.client :as http])
  (:require [clojure.data.json :as json]))

; you can totally tell I do JavaScript development LOL
(def ^{:private true} fns {

:get-balance '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:withdraw '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-new-address '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-my-addresses '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-address-received '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-address-by-label '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-difficulty '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-current-block '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-current-price '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))

})

(def ^{:private true} fns-v2 {

:create-user '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-user-address '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-user-balance '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:withdraw-from-user '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:move-to-user '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-users '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-network-hashrate '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))
:get-info '([] (http/get (str base-url "?api_key=" api-key "&a=get_balance")))

})

(def ^{:private true} version 0)

(comment
(defn- fn-with-stuff [fn-map]
  ; takes in map in format {:fn-name '([fn-args] fn-body)} and generates code for those functions
  (cond

    (nil? (resolve 'api-key))
     (map
       #(eval `(intern ~(symbol "dogeapi-clojure.core")
                       ~(symbol (apply str (rest (str (nth % 0)))))
                       (fn [& ~(quote stuff)]
                         (println "set api key first with (set-api-key [key])"))))
      fn-map
     )

    (nil? (resolve 'base-url))
     (map
      #(eval `(intern ~(symbol "dogeapi-clojure.core")
                      ~(symbol (apply str (rest (str (nth % 0)))))
                      (fn [& ~(quote stuff)]
                      (println "set version first with (set-endpoint-version [version])"))))
      fn-map
     )

    :else
    (if (and (= fn-map fns-v2) (= version 1))
      (map
        #(eval `(intern ~(symbol "dogeapi-clojure.core")
                        ~(symbol (apply str (rest (str (nth % 0)))))
                        (fn [& ~(quote stuff)]
                        (println "this functionality only available in v2 api."))))
       fn-map
      )

      (map
        #(eval `(intern ~(symbol "dogeapi-clojure.core")
                        ~(symbol (apply str (rest (str (nth % 0)))))
                        (fn ~(nth (nth % 1) 0)
                            ~(nth (nth % 1) 1))))
       fn-map
      )
    )
  )
)
)



(defn set-api-key [key]
  ; takes a string based on your API key
  (if (not (= (class key) java.lang.String)) (println "enter a valid string for API key")
      (def api-key key))
)

(defn- fn-with-stuff [fn-map]
  ; takes in map in format {:fn-name '([fn-args] fn-body)} and generates code for those functions
  (cond

    (nil? (resolve 'api-key))
     (map
       #(eval `(def
                       ~(symbol (apply str (rest (str (nth % 0)))))
                       (fn [& ~(quote stuff)]
                         (println "set api key first with (set-api-key [key])"))))
      fn-map
     )

    (nil? (resolve 'base-url))
     (map
      #(eval `(def
                      ~(symbol (apply str (rest (str (nth % 0)))))
                      (fn [& ~(quote stuff)]
                      (println "set version first with (set-endpoint-version [version])"))))
      fn-map
     )

    :else
    (if (and (= fn-map fns-v2) (= version 1))
      (map
        #(eval `(def
                        ~(symbol (apply str (rest (str (nth % 0)))))
                        (fn [& ~(quote stuff)]
                        (println "this functionality only available in v2 api."))))
       fn-map
      )

      (map
        #(eval `(def
                        ~(symbol (apply str (rest (str (nth % 0)))))
                        (fn ~(nth (nth % 1) 0)
                            ~(nth (nth % 1) 1))))
       fn-map
      )
    )
  )
)

(defn set-endpoint-version [v]
  ; takes an integer 1 or 2 so you can use either v1 or v2
  (do
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
  (println "funcs done")
  [(fn-with-stuff fns) (fn-with-stuff fns-v2)]
  )
)

(defn get-endpoint-version []
  (str "v" version))


