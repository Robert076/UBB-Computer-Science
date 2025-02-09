(defun multiplyNumericAtomsWithTheirLevel(tree curLevel)
    (cond
        ((numberp tree) (* tree curLevel))
        ((atom tree) tree)
        (t (mapcar #' (lambda(a) (multiplyNumericAtomsWithTheirLevel a (+ 1 curLevel))) tree))
    ))

(print(multiplyNumericAtomsWithTheirLevel '(1 A (2 (3) B)) 0))
