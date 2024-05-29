(ns handlers.blockchain
  (:import [java.security MessageDigest]))

(defonce blockchain (atom []))


(defn get-previous-block [chain]
  (last chain))

(defn create-block [chain nonce previous-hash hash transactions]
  (let [index (inc (count chain))
        timestamp (str (java.util.Date.))]
    {:index index
     :timestamp timestamp
     :nonce nonce
     :previousHash previous-hash
     :hash hash
     :transactions transactions}))

(defn sha256 [data]
  (let [digest (.digest (MessageDigest/getInstance "SHA-256") (.getBytes data "UTF-8"))]
    (apply str (map (partial format "%02x") digest))))

(defn proof-of-work [previous-nonce previous-hash transactions index]
  (loop [new-nonce 1]
    (let [hash-operation (sha256 (str (- (Math/pow new-nonce 2)
                                         (Math/pow previous-nonce 2)) transactions previous-hash index))]
      (if (and (= (subs hash-operation 0 4) "0000"))
        [new-nonce hash-operation]
        (recur (inc new-nonce))))))

(defn get-blockchain []
  (if (empty? @blockchain)
    (let [previousHash 0
          previous-nonce 0
          [nonce hash-operation] (proof-of-work previous-nonce previousHash [] 0)
          block (create-block @blockchain nonce previousHash hash-operation [])]
      (swap! blockchain conj block)
      {:message "congratulations, genesis block is created"
       :index (:index block)
       :timestamp (:timestamp block)
       :nonce nonce
       :previousHash previousHash
       :transactions []})
    ())
  @blockchain)

(defn mine-block [transactions]
  (let [previous-block (get-previous-block @blockchain)
        previous-nonce (:nonce previous-block)
        previousHash (:hash previous-block)
        [nonce hash-operation] (proof-of-work previous-nonce previousHash transactions (:index previous-block))
        block (create-block @blockchain nonce previousHash hash-operation transactions)]
    (swap! blockchain conj block)
    {:message "congratulations, you mined one block!"
     :index (:index block)
     :timestamp (:timestamp block)
     :nonce nonce
     :previousHash previousHash
     :transactions (:transactions block)}))