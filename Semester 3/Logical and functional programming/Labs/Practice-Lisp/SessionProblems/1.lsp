(defun replaceAtomsAtKthLevel(tree curLevel k)
    (cond
        ((and (= curLevel k) (atom tree)) 0)
        ((atom tree) tree)
        (t (mapcar #' (lambda(a) (replaceAtomsAtKthLevel a (+ 1 curLevel) k)) tree))
    ))

(print(replaceAtomsAtKthLevel '(a (1 (2 b)) (c (d))) 0 2))

