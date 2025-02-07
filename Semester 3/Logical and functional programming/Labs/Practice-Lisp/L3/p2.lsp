(defun summ(lst)
    (cond
        ((numberp lst) lst)
        ((atom lst) 0)
        (t (apply '+(mapcar #'summ lst)))
    ))

(print(summ '(1 2 3 (3 4 5) (6 (7)))))
