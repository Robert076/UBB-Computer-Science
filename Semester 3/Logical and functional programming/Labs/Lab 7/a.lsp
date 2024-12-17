(setq get-level-k
  (lambda (tree k)
    (cond ((= k 0) (list (car tree)))
          ((= (cadr tree) 0) nil)
          (t (apply 'append 
               (mapcar (lambda (n) 
                        (funcall get-level-k 
                                (list (nth (+ (* 2 n) 2) tree) 
                                      (nth (+ (* 2 n) 3) tree))
                                (- k 1)))
                      '(0 1)))))))

(print (funcall get-level-k '(A 2 B 0 C 2 D 0 E 0) 1))
