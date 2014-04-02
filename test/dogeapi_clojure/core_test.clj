(ns dogeapi-clojure.core-test
  (:require [clojure.test :refer :all]
            [dogeapi-clojure.core :refer :all]
            [clj-http.client :as http]
            [clojure.data.json :as json]))

(with-test
  (def wow (dogeapi-clojure.core/init-doge "115n7oq1mfkd541vd5u2cyjhhna" 1))
  (def base-url "https://www.dogeapi.com/wow/")
  (def api-url "https://www.dogeapi.com/wow/?api_key=115n7oq1mfkd541vd5u2cyjhhna")

  (def wow2 (dogeapi-clojure.core/init-doge "1e6fn9y1aik37y47buhqjj2qym" 2))
  (def base-url2 "https://www.dogeapi.com/wow/v2/")
  (def api-url2 "https://www.dogeapi.com/wow/v2/?api_key=1e6fn9y1aik37y47buhqjj2qym")

  (is (= (:body (http/get (str api-url "&a=get_balance"))) (dogeapi-clojure.core/get-balance wow)))
  (is (= (:body (http/get (str base-url "?a=get_current_block"))) (dogeapi-clojure.core/get-current-block wow)))
  (is (= (:body (http/get (str base-url "?a=get_difficulty"))) (dogeapi-clojure.core/get-difficulty wow)))
  (is (= (:body (http/get (str base-url "?a=get_current_price"))) (dogeapi-clojure.core/get-current-price wow)))

  (is (= (json/read-json (:body (http/get (str api-url2 "&a=get_balance")))) (dogeapi-clojure.core/get-balance wow2)))
  (is (= (json/read-json (:body (http/get (str base-url2 "?a=get_current_block")))) (dogeapi-clojure.core/get-current-block wow2)))
  (is (= (json/read-json (:body (http/get (str base-url2 "?a=get_difficulty")))) (dogeapi-clojure.core/get-difficulty wow2)))
  (is (= (json/read-json (:body (http/get (str base-url2 "?a=get_current_price")))) (dogeapi-clojure.core/get-current-price wow2)))
  (is (= (json/read-json (:body (http/get (str base-url2 "?a=get_network_hashrate")))) (dogeapi-clojure.core/get-network-hashrate wow2)))
  (is (= (json/read-json (:body (http/get (str base-url2 "?a=get_info")))) (dogeapi-clojure.core/get-info wow2)))
)
