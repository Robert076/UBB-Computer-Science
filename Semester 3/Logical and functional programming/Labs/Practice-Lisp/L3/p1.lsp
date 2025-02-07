(defun isElementInList(lst x)
    (cond
        ((null lst) nil)
        ((equalp (car lst) x) t)
        ((listp (car lst)) (or (isElementInList (car lst) x) (isElementInList (cdr lst) x)))
        (t (isElementInList (cdr lst) x))
    ))

(print(isElementInList '(1 2 3 (3 4 5) (6 (7))) 5))
