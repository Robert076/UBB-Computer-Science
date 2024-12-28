(defun removeOdds(lst)
    (cond
        ((null lst) nil)
        ((evenp (car lst)) (cons (car lst) (removeOdds (cdr lst))))
        (t (removeOdds(cdr lst)))))

(print(removeOdds '(1 -2 3 4 5 6 7)))