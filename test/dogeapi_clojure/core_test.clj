(ns dogeapi-clojure.core-test
  (:require [clojure.test :refer :all]
            [dogeapi-clojure.core :refer :all]
            [clj-http.client :as http]
            [clojure.data.json :as json]))

(with-test
  (def wow (dogeapi-clojure.core/init-doge "1e6fn9y1aik37y47buhqjj2qym" 2))
  (def base-url "https://www.dogeapi.com/wow/v2/")
  (def api-url "https://www.dogeapi.com/wow/v2/?api_key=1e6fn9y1aik37y47buhqjj2qym")

  (is (= (json/read-json (:body (http/get (str api-url "&a=get_balance")))) (dogeapi-clojure.core/get-balance wow)))
  (is (= (json/read-json (:body (http/get (str base-url "?a=get_current_block")))) (dogeapi-clojure.core/get-current-block wow)))
  (is (= (json/read-json (:body (http/get (str base-url "?a=get_current_price")))) (dogeapi-clojure.core/get-current-price wow)))
  (is (= (json/read-json (:body (http/get (str base-url "?a=get_network_hashrate")))) (dogeapi-clojure.core/get-network-hashrate wow)))
  (is (= (json/read-json (:body (http/get (str base-url "?a=get_info")))) (dogeapi-clojure.core/get-info wow)))
)
