(defun numPerLevel(lst)
    (cond
     ((null lst) 0)
     ((listp (car lst)) (numPerLevel(cdr lst)))
     ((numberp (car lst)) (+ 1 (numPerLevel(cdr lst))))
    ))
