(ns blockchain.handler
  (:require [compojure.core :refer [defroutes]]
            [compojure.route :as route]
            [routers.transactions :refer [transactions-routers]]
            [routers.blockchain :refer [blockchain-routers]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]))


(defroutes	app-routes
  transactions-routers
  blockchain-routers
  (route/not-found "Recurso	não	encontrado"))


(def app
  (-> (wrap-defaults app-routes api-defaults)
      (wrap-json-body {:keywords? true :bigdecimals? true})
      (wrap-cors
       :access-control-allow-origin [#".*"]
       :access-control-allow-methods [:get :post :put :delete :options]
       :access-control-allow-headers ["Content-Type" "Authorization"])))

