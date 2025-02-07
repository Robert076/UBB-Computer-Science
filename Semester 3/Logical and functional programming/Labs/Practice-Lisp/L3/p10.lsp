(defun maxDepth(l curLevel)
    (cond
        ((atom l) curLevel)
        (t (apply 'max (mapcar #' (lambda(a) (maxDepth a (+ 1 curLevel))) l)))
     ))

(print(maxDepth '(1 2 (2 (4 (6) 5)3 4 (5))) 0))
