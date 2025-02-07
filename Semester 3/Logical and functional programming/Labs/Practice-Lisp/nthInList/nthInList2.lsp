(defun getNthElement(lst cur n)
    (cond
        ((null lst) nil)
        ((equalp cur n) (car lst))
        ((< cur n) (getNthElement (cdr lst) (+ 1 cur) n))
     ))

(print(getNthElement '(1 2 3 4 5 6) 1 3))
