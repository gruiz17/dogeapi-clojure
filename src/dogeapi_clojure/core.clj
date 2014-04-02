(ns dogeapi-clojure.core
  (:require [clj-http.client :as http])
  (:require [clojure.data.json :as json]))

(defprotocol DogeMethods
  ^{
    :private true
   }
  (get-balance [this])
  (withdraw [this pin amount-doge payment-address])
  (get-new-address [this] [this address-label])
  (get-my-addresses [this])
  (get-address-received [this payment-address])
  (get-address-by-label [this address-label])
  (get-difficulty [this])
  (get-current-block [this])
  (get-current-price [this] [this convert-and-amount])

  (create-user [this user-id])
  (get-user-address [this user-id])
  (get-user-balance [this user-id])
  (withdraw-from-user [this pin amount-doge user-id payment-address])
  (move-to-user [this to-user-id from-user-id amount-doge])
  (get-users [this number])
  (get-transactions [this number] [this number opt-args])
  (get-network-hashrate [this])
  (get-info [this])
)

(defrecord Doge-v2 [api-url base-url]
  DogeMethods
  (get-balance [this] (json/read-json (:body (http/get (str api-url "&a=get_balance")))))
  (withdraw [this pin amount-doge payment-address]
    (if (<= amount-doge 5) (println "doge must be more than 5")
    (json/read-json (:body (http/get (str api-url "&a=withdraw&pin=" pin "&amount_doge=" amount-doge "&payment_address=" payment-address)))))
  )
  (get-new-address [this] (json/read-json (:body (http/get (str api-url "&a=get_balance")))))
  (get-new-address [this address-label] (json/read-json (:body (http/get (str api-url "&a=get_balance&address_label=" address-label)))))

  (get-my-addresses [this] (json/read-json (:body (http/get (str api-url "&a=get_my_addresses")))))

  (get-address-received [this payment-address-or-label]
    (cond (not (= clojure.lang.PersistentArrayMap (class payment-address-or-label))) (println "pass in either {:address-label [label]} or {:payment-address [address]} for the argument")
          (contains? payment-address-or-label :address-label) (json/read-json (:body (http/get (str api-url "&a=get_address_received&address_label=" (:address-label payment-address-or-label)))))
          (contains? payment-address-or-label :payment-address) (json/read-json (:body (http/get (str api-url "&a=get_address_received&payment_address=" (:payment-address payment-address-or-label)))))
          :else (println "pass in either {:address-label [label]} or {:payment-address [address]} for the argument"))
  )

  (get-address-by-label [this address-label] (json/read-json (:body (http/get (str api-url "&a=get_address_by_label&address_label=" address-label)))))
  (get-difficulty [this] (json/read-json (:body (http/get (str base-url "?a=get_difficulty")))))
  (get-current-block [this] (json/read-json (:body (http/get (str base-url "?a=get_current_block")))))
  (get-current-price [this] (json/read-json (:body (http/get (str base-url "?a=get_current_price")))))
  (get-current-price [this convert-and-amount]
                      (do
                      (def ^{:private true} current-price-url (str base-url "?a=get_current_price"))
                      (when (contains? convert-and-amount :convert-to) (def ^{:private true} current-price-url (str current-price-url "&convert_to=" (:convert-to convert-and-amount))))
                      (when (contains? convert-and-amount :amount-doge) (def ^{:private true} current-price-url (str current-price-url "&amount_doge=" (:amount-doge convert-and-amount))))
                      (json/read-json (:body (http/get (str current-price-url))))
                      )
  )

  (create-user [this user-id] (json/read-json (:body (http/get (str api-url "&a=create_user&user_id=" user-id)))))
  (get-user-address [this user-id] (json/read-json (:body (http/get (str api-url "&a=get_user_address&user_id=" user-id)))))
  (get-user-balance [this user-id] (json/read-json (:body (http/get (str api-url "&a=get_user_balance&user_id=" user-id)))))

  (withdraw-from-user [this pin user-id amount-doge payment-address]
    (json/read-json (:body (http/get (str api-url "&a=withdraw_from_user&pin=" pin "&user_id=" user-id "&amount_doge=" amount-doge "&payment_address=" payment-address))))
  )

  (move-to-user [this to-user-id from-user-id amount-doge]
    (json/read-json (:body (http/get (str api-url "&a=move_to_user&to_user_id=" to-user-id "&from_user_id=" from-user-id "&amount_doge=" amount-doge))))
  )

  (get-users [this number] (json/read-json (:body (http/get (str api-url "&a=get_users")))))
  (get-transactions [this number] (json/read-json (:body (http/get (str api-url "&a=get_transactions&num=" number)))))

  (get-transactions [this number opt-args]
    (do
      (def ^{:private true} get-transactions-url (str api-url "&a=get_transactions&num=" number))
      (when (contains? opt-args :user-id) (def ^{:private true} get-transactions-url (str get-transactions-url "&user_id=" (:user-id opt-args))))
      (when (contains? opt-args :type) (def ^{:private true} get-transactions-url (str get-transactions-url "&type=" (:type opt-args))))
      (when (contains? opt-args :label) (def ^{:private true} get-transactions-url (str get-transactions-url "&label=" (:label opt-args))))
      (when (contains? opt-args :payment-address) (def ^{:private true} get-transactions-url (str get-transactions-url "&payment_address=" (:payment-address opt-args))))
      (json/read-json (:body (http/get (str current-price-url))))
     )
  )

  (get-network-hashrate [this] (json/read-json (:body (http/get (str base-url "?a=get_network_hashrate")))))
  (get-info [this] (json/read-json (:body (http/get (str base-url "?a=get_info")))))
)

(defrecord Doge [api-url base-url]
  DogeMethods
  (get-balance [this] (:body (http/get (str api-url "&a=get_balance"))))
  (withdraw [this pin amount-doge payment-address]
    (if (<= amount-doge 5) (println "doge must be more than 5")
    (:body (http/get (str api-url "&a=withdraw&amount_doge=" amount-doge "&pin=" pin "&payment_address=" payment-address))))
  )
  (get-new-address [this] (:body (http/get (str api-url "&a=get_balance"))))
  (get-new-address [this address-label] (:body (http/get (str api-url "&a=get_balance&address_label=" address-label))))

  (get-my-addresses [this] (:body (http/get (str api-url "&a=get_my_addresses"))))
  (get-address-received [this payment-address-or-label]
    (cond (not (= clojure.lang.PersistentArrayMap (class payment-address-or-label))) (println "pass in either {:address-label [label]} or {:payment-address [address]} for the argument")
          (contains? payment-address-or-label :address-label) (:body (http/get (str api-url "&a=get_address_received&address_label=" (:address-label payment-address-or-label))))
          (contains? payment-address-or-label :payment-address) (:body (http/get (str api-url "&a=get_address_received&payment_address=" (:payment-address payment-address-or-label))))
          :else (println "pass in either {:address-label [label]} or {:payment-address [address]} for the argument"))
  )

  (get-address-by-label [this address-label] (:body (http/get (str api-url "&a=get_address_by_label&address_label=" address-label))))
  (get-difficulty [this] (:body (http/get (str base-url "?a=get_difficulty"))))
  (get-current-block [this] (:body (http/get (str base-url "?a=get_current_block"))))
  (get-current-price [this] (:body (http/get (str base-url "?a=get_current_price"))))
  (get-current-price [this convert-and-amount]
                      (do
                      (def ^{:private true} current-price-url (str base-url "?a=get_current_price"))
                      (when (contains? convert-and-amount :convert-to) (def ^{:private true} current-price-url (str current-price-url "&convert_to=" (:convert-to convert-and-amount))))
                      (when (contains? convert-and-amount :amount-doge) (def ^{:private true} current-price-url (str current-price-url "&amount_doge=" (:amount-doge convert-and-amount))))
                      (:body (http/get (str current-price-url)))
                      )
  )

  (create-user [this user-id] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-user-address [this user-id] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-user-balance [this user-id] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (withdraw-from-user [this pin amount-doge user-id payment-address] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (move-to-user [this to-user-id from-user-id amount-doge] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-users [this number] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-transactions [this number] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-network-hashrate [this] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))
  (get-info [this] (println "This functionality only in v2 API. Set with (init-doge api-key 2)."))

  ;; debating whether to add these despite them not being v1
  ;; (get-network-hashrate [this] (:body (http/get (str base-url "v2/?a=get_network_hashrate"))))
  ;; (get-info [this] (:body (http/get (str base-url "v2/?a=get_info"))))
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
