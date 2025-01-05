(defun numberSublists(lst)
            (cond
                ((null lst) 0)
                ((not(listp(car lst))) (numberSublists(cdr lst)))
                ((listp(car lst)) (+ 1 (numberSublists(car lst)) (numberSublists(cdr lst))))
                ))

(print (numberSublists '(1 2 3 (3) (4 (5)) 5)))