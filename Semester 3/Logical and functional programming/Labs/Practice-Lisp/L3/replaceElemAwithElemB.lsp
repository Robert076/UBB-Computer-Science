(defun p12(l elemOld elemNew)
    (cond
        ((and (atom l) (equal elemOld l)) elemNew)
        ((atom l) l)
        (t (mapcar #' (lambda (a) (p12 a elemOld elemNew)) l))
    ))

(print (p12 '(1 2 3 4 (5 6 4 (4 5) 3) 2 2) 4 -1))
