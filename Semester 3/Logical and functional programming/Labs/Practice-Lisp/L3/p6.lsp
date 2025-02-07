(defun getMax(l)
    (cond
        ((numberp l) l)
        ((atom l) 0)
        (t (apply #'max (mapcar #'getMax l)))
    ))

(print(getMax '(12 3 4 (55) 3 4)))
