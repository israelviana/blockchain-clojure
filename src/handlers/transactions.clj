(ns handlers.transactions (:require [helpers.uuid :refer [generate-id]]))

(defonce transactions (atom []))

(defn get-all-transactions []
  @transactions)

(defn create-transaction [request]
  (let [transaction (assoc request :id (generate-id))]
    (swap! transactions conj transaction)
    {:message "your transactions was saved successfully"
     :id  (:id transaction)
     :payment-type (:payment-type transaction)
     :amount (:amount transaction)}))

