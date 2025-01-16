(defun makezs(lst k cur)
    (mapcar
     (lambda (x)
      (cond
       ((and (numberp x) (= k cur)) 0)
       ((listp x) (makezs x k (+ 1 cur)))
       (t x)
      )
     ) 
     lst))

(print(makezs '(1 2 (3) (4) (4 (5))) 2 1))
