(defun dotProduct (v1 v2)
  (cond
    ((or (null v1) (null v2))  
     0)                        
    (t
     (+ (* (car v1) (car v2))  
        (dotProduct (cdr v1) (cdr v2)))))) 

(print (dotProduct '(1 2 3) '(4 5 6)))




; dotProduct(l1l2..ln, p1p2..pm) = 0, n = 0
;                                  0, m = 0
;                                  l1 * p1 + dotProduct(l2..ln, p2..pm), n != 0, m != 0