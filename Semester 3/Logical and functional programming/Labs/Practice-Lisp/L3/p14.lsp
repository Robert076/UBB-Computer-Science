(defun p14(l)
    (cond
        ((atom l) 1)
        (t (apply '+ (mapcar #' p14 l)))
    ))

(print (p14 '(1 2 3 (4 5 (6)) 7) ) )
