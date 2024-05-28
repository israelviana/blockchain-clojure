(ns helpers.json
   (:require [cheshire.core :as json]))

(defn json-format [body]
  {:headers	{"Content-Type" "application/json;	charset=utf-8"}
   :body	(json/generate-string body)})
