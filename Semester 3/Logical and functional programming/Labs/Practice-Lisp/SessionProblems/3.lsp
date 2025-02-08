(defun removeMultiplesOf3(l)
    (cond
        ((and (numberp l) (= (mod l 3) 0)) nil)
        ((atom l) l)
        (t (mapcar #' removeMultiplesOf3 l))
    ))

(print(removeMultiplesOf3 '(1(2 A (3 A)) (6))))
