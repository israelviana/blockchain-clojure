(ns helpers.uuid)

(defn generate-id []
  (str (java.util.UUID/randomUUID)))