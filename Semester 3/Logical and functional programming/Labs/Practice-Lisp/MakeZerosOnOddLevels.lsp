(defun makezsodd(lst cur)
    (mapcar
     (lambda (x)
      (cond
       ((and (numberp x) (oddp cur)) 0)
       ((listp x) (makezsodd x (+ 1 cur)))
       (t x)
      )
     ) 
     lst))

(print(makezsodd '(1 2 (3) (4) (4 (5))) 1))
