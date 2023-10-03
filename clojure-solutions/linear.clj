(defn sizes-is-equals? [x] (apply == (map count x)))

(defn is-vector? [x] (and (vector? x) (every? number? x)))
(defn is-matrix? [x] (and (vector? x) (every? is-vector? x)))

(defn pre-vector [x] (and (every? is-vector? x) (sizes-is-equals? x)))
(defn pre-matrix [x] (and (every? is-matrix? x) (sizes-is-equals? x) (sizes-is-equals? (mapv first x))))

(defn clear [argument] (if (number? argument) [] (if (vector? argument) (mapv clear argument) argument)))
(defn is-tensor? [argument empty-argument]
  (or (and (vector? argument) (apply = empty-argument) (every? true? (mapv is-tensor? argument empty-argument))) (is-vector? argument)))
(defn pre-tensor [argument] (is-tensor? argument (clear argument)))

(defn tensor-operation-fabric [operation & tensor] {:pre [(pre-tensor (vec tensor))]}
  (if (number? (first tensor)) (apply operation tensor) (apply mapv (partial tensor-operation-fabric operation) tensor)))

(defn transpose [matrix]
  {:pre [(is-matrix? matrix)]} (apply mapv vector matrix))

(defn vect [& vec]
  {:pre [(and (pre-vector vec) (== (count (first vec)) 3))]}
  (reduce (fn [first-argument second-argument]
            (vector (- (* (nth first-argument 1) (nth second-argument 2)) (* (nth first-argument 2) (nth second-argument 1)))
                    (- (* (nth first-argument 2) (nth second-argument 0)) (* (nth first-argument 0) (nth second-argument 2)))
                    (- (* (nth first-argument 0) (nth second-argument 1)) (* (nth first-argument 1) (nth second-argument 0))))) vec))

(defn operation-fabric [operation pre]
  (letfn [(get-result [& arguments] (apply mapv (if (every? is-vector? arguments) operation get-result) arguments))]
    (fn [& arguments] {:pre [(pre arguments)]} (apply get-result arguments))))

(def v+ (operation-fabric + pre-vector))
(def m+ (operation-fabric + pre-matrix))
(defn t+ [& argument] (apply (partial tensor-operation-fabric +) argument))

(def v- (operation-fabric - pre-vector))
(def m- (operation-fabric - pre-matrix))
(defn t- [& argument] (apply (partial tensor-operation-fabric -) argument))

(def v* (operation-fabric * pre-vector))
(def m* (operation-fabric * pre-matrix))
(defn t* [& argument] (apply (partial tensor-operation-fabric *) argument))

(def vd (operation-fabric / pre-vector))
(def md (operation-fabric / pre-matrix))
(defn td [& argument] (apply (partial tensor-operation-fabric /) argument))

(defn scalar [& scalar]
  {:pre [(pre-vector scalar)]} (apply + (apply v* scalar)))

(defn m*v [matrix & vec]
  {:pre [(and (is-matrix? matrix) (pre-vector vec))]}
  (mapv (fn [argument] (apply + (apply v* argument vec)))  matrix))

(defn m*m [& matrix]
  {:pre [(and (every? is-matrix? matrix) (every? sizes-is-equals? matrix))]}
  (reduce (fn [first-matrix second-matrix] (mapv (fn [argument] (m*v (transpose second-matrix) argument)) first-matrix)) matrix))

(defn *s [operation pre]
  (fn [vector-or-matrix & scalar] {:pre [(and (pre (vector vector-or-matrix)) (every? number? scalar))]}
    (mapv (fn [argument] (operation argument (apply * scalar))) vector-or-matrix)))

(def v*s (*s * pre-vector))
(def m*s (*s v*s pre-matrix))

