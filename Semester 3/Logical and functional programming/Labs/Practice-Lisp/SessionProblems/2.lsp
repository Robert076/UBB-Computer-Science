(defun replaceNodesOnOddLevelsWithE(tree curLevel e)
    (cond
        ((and (= (mod curLevel 2) 1) (atom tree)) e)
        ((atom tree) tree)
        (t (mapcar #' (lambda(a) (replaceNodesOnOddLevelsWithE a (+ 1 curLevel) e)) tree))
    ))

(defun main(tree e) (replaceNodesOnOddLevelsWithE tree 0 e))

(print (main '(a (b (g)) (c (d (e)) (f))) 2))
