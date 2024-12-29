(defun countElems(lst)
    (cond
        ((null lst) 0)
        (t (+ 1 (countElems (cdr lst))))))

(print(countElems '(1 2 3 4 5 6)))