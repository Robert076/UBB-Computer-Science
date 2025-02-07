(defun productList(l)
    (cond
        ((numberp l) l)
        ((atom l) 1)
        (t (apply '* (mapcar #'productList l)))
    ))

(print(productList '(1 2 (69) 3)))
