;old
(defun f(l)
    (cond
        ((null l) nil)
        ((listp (car l)) (append (f (car l)) (f (cdr l)) (car (f (car l)))))
        (t (list(car l)))
    ))
;new
(defun f(l)
    (cond
        ((null l) nil)
        ((listp (car l)) 
         ((lambda (a) (append a (f (cdr l)) (car a))) (f (car l))))
        (t (list(car l)))
    ))

; old
(defun f(g l)
    (cond
        ((null l) nil)
        ((> (funcall g l) 0) (cons (funcall g l) (f (cdr l))))
        (t (funcall g l))
    ))
; new
(defun f(g l)
    ((lambda(a)
        (cond
            ((null l) nil)
            ((> a 0) (cons a (f (cdr l))))
            (t a)
        )) (funcall g l)))
