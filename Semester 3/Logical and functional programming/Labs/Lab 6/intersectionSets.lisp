(defun intersection(s1 s2)
    (cond
        ((or (null s1) (null s2)) nil)
        ((isInSet (car s1) s2) 
            (cons (car s1) (intersection (cdr s1) s2)))
        (t (intersection (cdr s1) s2))
    )
)

(defun isInSet(x s1)
    (cond
        ((null s1) nil)
        ((= x (car s1)) t)
        (t (isInSet x (cdr s1)))))

;intersection(l1..ln, p1..pm) = [], n or m = 0
;                             = [l1] U intersection(l2..ln, p1..pm) if l1 in p1..pm
;                             = intersection(l2..ln, p1..pm) othrwise
;
;   isInSet(x, l1..ln) = false, n = 0
;                        true, x = l1
;                        isInSet(x, l2..ln) otherwise)
;