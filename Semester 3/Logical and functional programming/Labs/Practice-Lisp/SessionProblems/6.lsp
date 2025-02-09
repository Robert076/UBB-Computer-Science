(defun check(l)
    (cond
        ((cdr l) (check (cdr l))) 
        (t (not (numberp (car l))))
    ))

(defun countLists(l)
    (cond
        ((atom l) 0)
        ((check l) (+ 1 (apply '+ (mapcar #' countLists l))))
        (t (apply '+ (mapcar #' countLists l)))
    ))

(print(countLists '(1 2 (3 (C) (2 3) B) A)))
