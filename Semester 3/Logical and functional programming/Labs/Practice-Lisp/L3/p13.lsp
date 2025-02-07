(defun p13(tree curLevel)
    (cond
        ((atom tree) curLevel)
        (t (apply 'max (mapcar #' (lambda(a) (p13 a (+ 1 curLevel))) tree)))
    ))

(print(p13 '(1 2 (3 4 (5)) 6 7 (8(9))) 0))
