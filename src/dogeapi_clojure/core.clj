(ns dogeapi-clojure.core
  (:require [clj-http.client :as http])
  (:require [clojure.data.json :as json]))

(defprotocol DogeMethods
  ^{
    :private true
   }
  (get-balance [this])
  (withdraw [this pin amount-doge payment-address])
  (get-new-address [this])
  (get-my-addresses [this])
  (get-address-received [this payment-address])
  (get-address-by-label [this address-label])
  (get-difficulty [this])
  (get-current-block [this])
  (get-current-price [this amount])

  (create-user [this user-id])
  (get-user-address [this user-id])
  (get-user-balance [this user-id])
  (withdraw-from-user [this pin amount-doge user-id payment-address])
  (move-to-user [this to-user-id from-user-id amount-doge])
  (get-users [this number])
  (get-transactions [this number])
  (get-network-hashrate [this])
  (get-info [this])
)

(defrecord Doge-v2 [api-url base-url]
  DogeMethods
  (get-balance [this] (:body (http/get (str api-url "&a=get_balance"))))
  (withdraw [this pin amount-doge payment-address] (http/get (str api-url "&a=get_balance")))
  (get-new-address [this] (http/get (str api-url "&a=get_balance")))
  (get-my-addresses [this] (http/get (str api-url "&a=get_balance")))
  (get-address-received [this payment-address] (http/get (str api-url "&a=get_balance")))
  (get-address-by-label [this address-label] (http/get (str api-url "&a=get_balance")))
  (get-difficulty [this] (:body (http/get (str base-url "?a=get_difficulty"))))
  (get-current-block [this] (:body (http/get (str base-url "?a=get_current_block"))))
  (get-current-price [this amount] (http/get (str base-url "?a=get_current_price")))

  (create-user [this user-id] (http/get (str api-url "&a=get_balance")))
  (get-user-address [this user-id] (http/get (str api-url "&a=get_balance")))
  (get-user-balance [this user-id] (http/get (str api-url "&a=get_balance")))
  (withdraw-from-user [this pin amount-doge user-id payment-address] (http/get (str api-url "&a=get_balance")))
  (move-to-user [this to-user-id from-user-id amount-doge] (http/get (str api-url "&a=get_balance")))
  (get-users [this number] (http/get (str api-url "&a=get_balance")))
  (get-transactions [this number] (http/get (str api-url "&a=get_balance")))
  (get-network-hashrate [this] (:body (http/get (str base-url "?a=get_network_hashrate"))))
  (get-info [this] (:body (http/get (str base-url "?a=get_info"))))
)

(defrecord Doge [api-url base-url]
  DogeMethods
  (get-balance [this] (:body (http/get (str api-url "&a=get_balance"))))
  (withdraw [this pin amount-doge payment-address] (http/get (str api-url "&a=get_balance")))
  (get-new-address [this] (http/get (str api-url "&a=get_balance")))
  (get-my-addresses [this] (http/get (str api-url "&a=get_balance")))
  (get-address-received [this payment-address] (http/get (str api-url "&a=get_balance")))
  (get-address-by-label [this address-label] (http/get (str api-url "&a=get_balance")))
  (get-difficulty [this] (:body (http/get (str base-url "?a=get_difficulty"))))
  (get-current-block [this] (:body (http/get (str base-url "?a=get_current_block"))))
  (get-current-price [this amount] (http/get (str base-url "?a=get_current_price")))

  (get-network-hashrate [this] (:body (http/get (str base-url "v2/?a=get_network_hashrate"))))
  (get-info [this] (:body (http/get (str base-url "v2/?a=get_info"))))

  (create-user [this user-id] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-user-address [this user-id] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-user-balance [this user-id] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (withdraw-from-user [this pin amount-doge user-id payment-address] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (move-to-user [this to-user-id from-user-id amount-doge] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-users [this number] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-transactions [this number] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
)

(defn init-doge [api-key version]
  (cond
   (not (= java.lang.String (class api-key))) (println "set api key to valid string")
   (not (or (= java.lang.Long (class version)) (= java.lang.Integer (class version)))) (println "set version to valid integer")
   (= version 2) (Doge-v2. (str "https://www.dogeapi.com/wow/v2/?api_key=" api-key) "https://www.dogeapi.com/wow/v2/")
   (= version 1) (Doge. (str "https://www.dogeapi.com/wow/?api_key=" api-key) "https://www.dogeapi.com/wow/")
   :else
   (println "set version to either 1 or 2")
  )
)



