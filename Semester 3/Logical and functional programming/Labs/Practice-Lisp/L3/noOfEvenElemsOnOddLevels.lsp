; An n-ary tree of form (root (subtree 1) ...) is given. Find the total number of even numeric atoms on odd levels


(defun cnt(l curLevel)
    (cond
        ((and (and (numberp l) (= (mod curLevel 2) 1)) (= (mod l 2) 0)) 1)
        ((atom l) 0)
        (t (apply '+ (mapcar #' (lambda(a) (cnt a (+ 1 curLevel))) l)))
    ))

(print(cnt '(1 (2 3)) 1))
