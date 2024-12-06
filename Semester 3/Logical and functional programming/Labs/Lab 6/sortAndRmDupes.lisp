(defun sortList (lst)
  (if (null lst)
      nil
      (let ((x (car lst))
            (sorted-tail (sortList (cdr lst))))
        (if (member x sorted-tail)
            sorted-tail
            (insert x sorted-tail)))))

(defun insert (x lst)
  (cond ((null lst) (list x))
        ((< x (car lst)) (cons x lst))
        (t (cons (car lst) (insert x (cdr lst))))))
(print (sortList '(4 2 2 5 1 4 3)))


; sortList(l1..ln) = nil, n = 0
;                    l2..ln, l1 in l2..ln
;                    insert(l1, l2..ln), l1 not in l2..ln
;
;
; insert(x, l1..ln) = [x], n = 0
;                     x U l1..ln, x < l1
;                     l1 U insert(l2..ln)
;
