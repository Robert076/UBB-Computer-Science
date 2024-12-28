(defun sum-positive(lst)
    (cond
    ((null lst) 0)
    ((> (car lst) 0) (+ (car lst) (sum-positive (cdr lst))))
    (t (sum-positive (cdr lst)))))

(print(sum-positive '(1 -2 3 4)))