(def constant constantly)
(defn variable [var]
  (fn [vars]
    (vars var)))

(defn operation-factory [op]
  (fn [& args]
    (fn [vars]
      (apply
        op
        (mapv
          #(% vars)
          args)))))

(defn d
  ([x] (/ 1.0 x))
  ([x & args] (/ (double x) (apply * args))))

(def add
  (operation-factory +))
(def multiply
  (operation-factory *))
(def subtract
  (operation-factory -))
(def negate
  subtract)
(def divide
  (operation-factory d))
(defn sumexp-op [& args]
  (apply + (mapv #(Math/exp %) args)))
(defn lse-op [& args]
  (Math/log (apply sumexp-op args)))
(def sumexp
  (operation-factory sumexp-op))
(def lse
  (operation-factory lse-op))

(def operations
  {
   '- subtract
   '+ add
   '/ divide
   '* multiply
   'negate negate
   'sumexp sumexp
   'lse lse
   })

(defn parseFunction [expression]
  (-> expression
      (read-string)
      ((fn recur-parsing [exp]
         (cond
           (seq? exp) (apply (operations (first exp)) (map recur-parsing (rest exp)))
           (symbol? exp) (variable (str exp))
           (number? exp) (constant exp))))))













(definterface abstractExpression
  (abstractEvaluate [args])
  (abstractToString [])
  (abstractDiff [arg]))

(declare ZERO)
(deftype Const [arg]
  abstractExpression
    (abstractEvaluate [_ _]
      arg)
    (abstractToString [_]
      (str arg))
    (abstractDiff [_ _]
                  ZERO))

(def ZERO (Const. 0))
(def ONE (Const. 1))
(def TWO (Const. 2))

(deftype Var [var]
  abstractExpression
    (abstractEvaluate [_ args]
      (args var))
    (abstractToString [_]
      var)
    (abstractDiff [_ diffVar]
      (if
        (= diffVar var)
        ONE
        ZERO)))

(deftype abstractOperation [op signOp diffOp args]
  abstractExpression
    (abstractEvaluate [_ vars]
      (apply
        op
        (mapv
          #(.abstractEvaluate
             %
             vars)
          args)))
    (abstractToString [_]
      (str
        "("
        signOp
        " "
        (clojure.string/join " " (map #(.abstractToString %) args))
        ")"))
    (abstractDiff [_ diffVar]
      (diffOp diffVar)))

(defn Add [& args]
  (abstractOperation.
    +
    '+
    (fn [diffVar]
      (apply
        Add
        (map
          #(.abstractDiff % diffVar)
          args)))
    args))
(defn Subtract [& args]
  (abstractOperation.
    -
    '-
    (fn [diffVar]
      (apply
        Subtract
        (map
          #(.abstractDiff % diffVar)
          args)))
    args))

(declare Divide)

(defn Multiply [& args]
  (abstractOperation.
    *
    '*
    (fn [diffVar]
      (if
        (= 1 (count args))
        (.abstractDiff (first args) diffVar)
        (Add
          (Multiply
            (.abstractDiff (first args) diffVar)
            (apply Multiply (rest args)))
          (Multiply
            (.abstractDiff (apply Multiply (rest args)) diffVar)
            (first args)))))
    args))

(defn Divide [& args]
  (abstractOperation.
    d
    '/
    (fn [diffVar]
      (if (= 1 (count args))
        (Divide
          (Subtract
            (Multiply
              ;; производную от 1 знаем
              (.abstractDiff
                ONE
                diffVar)
              (first args))
            (Multiply
              ONE
              (.abstractDiff
                (first args)
                diffVar)))
          (Multiply
            (first args)
            (first args)))
        (Divide
          (Subtract
            (Multiply
              (.abstractDiff
                (first args) diffVar)
              (apply Multiply (rest args)))
            (Multiply
              (first args)
              (.abstractDiff
                (apply Multiply (rest args)) diffVar)))
          (Multiply
            (apply Multiply (rest args))
            (apply Multiply (rest args))))))
    args))
(defn Negate [& args]
  (abstractOperation.
    -
    'negate
    (fn [diffVar]
      (apply
        Subtract
        (map
          #(.abstractDiff % diffVar)
          args)))
    args))

(defn meansq-operation
  [& args] (apply + (map #(/ (* % %) (count args)) args)))

(defn rms-operation
  [& args] (Math/sqrt (apply meansq-operation args)))

(defn Meansq [& args]
  (abstractOperation.
    meansq-operation
    'meansq
    (fn [diffVar]
      (.abstractDiff
        (apply Add (map #(Divide
                           (Multiply % %)
                           (Const. (count args))) args))
        diffVar))
    args))

(defn RMS [& args]
  (abstractOperation.
    rms-operation
    'rms
    (fn [diffVar]
      (Divide
        (.abstractDiff (apply Meansq args) diffVar)
        (Multiply
          TWO
          (apply RMS args))))
    args))

(defn Constant [x]
  (Const. x))
(defn Variable [var]
  (Var. var))
(defn evaluate [object args]
  (.abstractEvaluate object args))
(defn toString [object]
  (.abstractToString object))
(defn diff [object diffVar]
  (.abstractDiff object diffVar))

(def Operations
  {
   '- Subtract
   '+ Add
   '/ Divide
   '* Multiply
   'negate Negate
   'meansq Meansq
   'rms RMS
   })

;; копипаста parseFunction
(defn parseObject [expression]
  (-> expression
      (read-string)
      ((fn recur-parsing [exp]
         (cond
           (seq? exp) (apply (Operations (first exp)) (map recur-parsing (rest exp)))
           (symbol? exp) (Variable (str exp))
           (number? exp) (Constant exp))))))

(def expr (lse (variable "x")))

(println (expr {"z" 0.0, "x" 0.0, "y" 0.0}))

