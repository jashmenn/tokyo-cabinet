
(ns user 
  (:use tokyo-cabinet))  ;; bring into our namespace

(defn -main [& args]

  (with-cabinet { :filename "test.tokyo" :mode (+ OWRITER OCREAT) } 
    (doseq [[name val] [["1" "one"] ["2" "two"] ["3" "three"]]]
      (put-value-async name val))
    (synchronize))

  (with-cabinet { :filename "test.tokyo" :mode OREADER }
    (println (primary-keys)))

 (time 
  (with-cabinet { :filename "test.tokyo" :mode (+ OWRITER OCREAT) } 
    (time (doseq [[name val] (repeatedly 3000000 (fn [] [(str (rand-int 1000000000)) (str (rand-int 1000000000))]))]
       (put-value-async name val)))))

  ;; tables

  (def params { :filename "test-table.tokyo" :mode (+ OWRITER OCREAT) :type :table } )
  (with-cabinet params
    (put-value "foo/x" { :name "John Doe" :hobbies "rowing fishing skiing" :age 28 :gender "M" })
    (put-value "bar/x" { :name "Melissa Swift" :hobbies "soccer tennis books" :age 33 :gender "F"})
    (put-value "c" { :name "Tom Swift" :hobbies "inventing exploring" :gender "M" })
    (put-value "d" { :name "Harry Potter" :hobbies "magic quidditch flying" :gender "M" :age 9 }))

  (with-cabinet params
    (println (get-value "a/p"))))

(comment

 (time 
  (with-cabinet { :filename "test.tokyo" :mode (+ OWRITER OCREAT) :type :bplus :bnum 4000000 :lmemb 512 :nmemb 512} 
    (time (doseq [[name val] (repeatedly 1000000 (fn [] [(str (rand-int 1000000000)) (str (rand-int 1000000000))]))]
            (put-value name val)))))

 )

