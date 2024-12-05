(defun listDepth (lst)
  (if (null lst)
      1
      (if (listp (car lst))
          (1+ (listDepth (car lst)))
          (listDepth (cdr lst)))))

(print (listDepth '((1 (2 3)) (4 (5 6)))))  


listDepth(l1..ln) = 1, n = 0
                    1 + listDepth(l1), l1 list
                    listDepth(l2..ln)

                    