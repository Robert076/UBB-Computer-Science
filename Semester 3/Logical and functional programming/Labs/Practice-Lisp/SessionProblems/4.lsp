(defun incrementEvens(l)
    (cond
        ((and (numberp l) (= (mod l 2) 0)) (+ l 1))
        ((atom l) l)
        (t (mapcar #' incrementEvens l))
    ))

(print(incrementEvens '(1 2 3 (3 4 5 (6 ( 5 4)7 8)))))
