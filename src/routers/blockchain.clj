(ns routers.blockchain (:require [compojure.core :refer [defroutes GET POST]]
                                  [helpers.json :refer [json-format]]
                                  [handlers.blockchain :refer [get-blockchain mine-block]]))


(defroutes blockchain-routers
  (GET "/blockchain" [] (json-format (get-blockchain)))
  (POST "/blockchain" request (-> (mine-block (:body request))
                                    (json-format))))