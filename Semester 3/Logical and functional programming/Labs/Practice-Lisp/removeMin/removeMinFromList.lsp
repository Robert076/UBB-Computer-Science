(defun findMin(lst)
    (cond
        ((null lst) nil)
        ((null (cdr lst)) (car lst))
        (t (min (car lst) (findMin (cdr lst))))))


(defun removeElemFromList(lst x)
    (cond
        ((null lst) nil)
        ((= (car lst) x) (removeElemFromList (cdr lst) x))
        (t (cons (car lst) (removeElemFromList (cdr lst) x)))
        ))

(defun removeMinFromList(lst)
    (cond
        (t (removeElemFromList lst (findMin lst)))
        ))

(print(removeMinFromList '(1 0 3)))
