(defun mynth(lst n cnt)
    (cond
        ((= n cnt) (car lst))
        ((> n cnt) (mynth (cdr lst) n (+ 1 cnt)))))

(print (mynth '(1 2 3) 2 0))  