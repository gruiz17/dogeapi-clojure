# dogeapi-clojure

A DogeAPI wrapper for clojure.

## Usage

Declare dogeapi-clojure in your project.clj file.


    (defproject whatever "x.x.x-SNAPSHOT"
      :dependencies [[dogeapi-clojure "0.1.0"]])


To use:

    ;; include dogeapi-clojure
    (require '[dogeapi-clojure.core :as doge])

    ;; create instance of doge with your api key and the api endpoint you want to use
    ;; version-number is either 1 or 2
    ;; note that some functionality is unavailable through version 1
    (def wow (init-doge "your-api-key" version-number))

If you're using v2, all the methods will return a map, like so:

    ;; example for calling get-balance
    => (doge/get-balance wow)
    {:data {:balance 1.9}}

If you're using v1, all the methods will return a string, like so:

    ;; example for calling get-balance
    => (doge/get-balance wow)
    "1.9"

## Individual functions

For more information on what these methods do, go to the [DogeAPI documentation](https://www.dogeapi.com/api_documentation).

get\_balance

    (doge/get-balance wow)

withdraw

    (doge/withdraw wow pin amount-doge payment-address)

get\_new\_address

    (doge/get-new-address wow [this address-label])

get\_my\_addresses

    (doge/get-my-addresses wow)

get\_address\_received

    ;; use either
    (doge/get-address-received wow {:payment-address "payment-address"})
    ;; or
    (doge/get-address-received wow {:address-label "address-label"})

get\_address\_by\_label

    (doge/get-address-by-label wow "address-label")

get\_difficulty

    (doge/get-difficulty wow)

get\_current\_block

    (doge/get-current-block wow)

get\_current\_price

    (doge/get-current-price wow)
    ;; you can also pass in a map with a :convert-to option and an :amount-doge option (both of which are optional)
    ;; convert-to defaults to USD by default
    (doge/get-current-price wow {:convert-to "BTC" :amount-doge some-number-of-doge})

### v2 functions

create\_user

    (doge/create-user wow user-id])

get\_user\_address

    (doge/get-user-address wow user-id)

get\_user\_balance

    (doge/get-user-balance wow user-id)

withdraw\_from\_user

    (doge/withdraw-from-user wow pin amount-doge user-id payment-address)

move\_to\_user

    (doge/move-to-user wow to-user-id from-user-id amount-doge)

get_users

    (doge/get-users wow number)

get\_transactions

    (doge/get-transactions wow number)
    ;; optional arguments are :user-id, :payment-address, :label, and :type
    ;; they are passed in after the number argument in a single map
    (doge/get-transactions wow number {:user-id "user-id"
                                       :payment-address "payment-address"
                                       :label "label"
                                       :type "type"})

get\_network\_hashrate

    (doge/get-network-hashrate wow)

get\_info

    (doge/get-info wow)

## License

Copyright © 2014 Gabriel Ruiz

Distributed under the MIT License.
