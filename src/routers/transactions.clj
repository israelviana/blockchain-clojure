(ns routers.transactions
  (:require [compojure.core :refer [defroutes GET POST]]
            [helpers.json :refer [json-format]]
            [handlers.transactions :refer [get-all-transactions create-transaction]]))



(defroutes transactions-routers
  (GET "/transactions" [] (json-format (get-all-transactions)))
  (POST "/transactions" request (-> (create-transaction (:body request))
                                    (json-format))))
