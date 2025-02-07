(defun tripleSum(x)
    (cond
        ((numberp x) (* x 3))
        ((atom x) x)
        (t (apply '+ (mapcar #'tripleSum x)))
    ))

(print (tripleSum '(1 2 3)))
