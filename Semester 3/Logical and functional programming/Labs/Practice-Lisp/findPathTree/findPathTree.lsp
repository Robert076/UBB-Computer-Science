; find the path from the root node of a tree represented as : A 2 B 0 C 2 D 0 E 0 to a given node

(defun path(tree elem)
    (cond
        ((null tree) nil)
        ((= (car tree) elem) (list elem))
        ((isInRightSubtree tree elem) (cons (car tree) (path (right tree) elem)))
        ((isInLeftSubtree tree elem) (cons (car tree) (path (left tree) elem)))
        ))

(defun isInRightSubtree(tree elem)
    (checkExistence (right tree) elem))

(defun isInLeftSubtree(tree elem)
    (checkExistence (left tree) elem))

(defun checkExistence(tree elem)
    (cond
        ((null tree) nil)
        ((equal (car tree) elem) t)
        (t (checkExistence(cdr tree) elem))
        ))

(defun right(tree)
    (traverseRightSubtree (cddr tree) 0 0))

(defun left(tree)
    (traverseLeftSubtree (cddr tree) 0 0))

(defun traverseRightSubtree(tree numNodes numEdges)
    (cond
        ((null tree) nil)
        ((= (+ 1 numEdges) numNodes) tree)
        (t (traverseRightSubtree (cddr tree) (+ 1 numNodes) (+ (cadr tree) numEdges)))
        ))

(defun traverseLeftSubtree(tree numNodes numEdges)
    (cond
        ((null tree) nil)
        ((= (+ 1 numEdges) numNodes) nil)
        (t (traverseLeftSubtree(cddr tree) (+ 1 numNodes) (+ (cadr tree) numEdges)))
        ))

(print(path '(1 2 2 0 3 2 4 0 5 0) 5))
