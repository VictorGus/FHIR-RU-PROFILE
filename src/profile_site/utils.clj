(ns profile-site.utils)

(defn vector-first-path [is? tree]
  (loop [[tree i path fk] [tree 0 [] nil]]
    (cond
      (>= i (count tree))
      (if (nil? fk) nil (recur fk))
      (vector? (tree i))
      (recur [(tree i) 0 (conj path i) [tree (inc i) path fk]])
      (is? (tree i))
      (conj path i)
      :else
      (recur [tree (inc i) path fk]))))

(defn *get [m k]
  (cond (map? m) (get m k)
        (and (sequential? m) (integer? k)) (nth m k nil)))

(defn *get-in [m ks]
  (reduce *get m ks))

(defn get-inner-attrs [attr]
  (assoc [{:desc (*get-in attr [1 :desc])
           :isRequired (*get-in attr [1 :isRequired])
           :type (*get-in attr [1 :type])
           :attr (name (*get attr 0))}]
         1
         (for [inner (*get-in attr [1 :attrs])]
           (if (*get-in inner [1 :attrs])
             (get-inner-attrs inner)
             {:desc (*get-in inner [1 :desc])
              :isRequired (*get-in inner [1 :isRequired])
              :type (*get-in inner [1 :type])
              :attr (name (*get inner 0))}))))

(defn get-profile-attrs [{attrs :attrs}]
  (for [itm attrs]
     (get-inner-attrs itm)))
