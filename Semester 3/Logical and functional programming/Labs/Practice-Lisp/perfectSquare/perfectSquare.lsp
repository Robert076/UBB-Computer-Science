(defun isPerfectSquare(num i)
    (cond
        ((< num 1) "Not perfect square")
        ((= (* i i) num) "Perfect square")
        ((>= i num) "Not perfect square")
        (t (isPerfectSquare num (+ i 1)))))

(print( isPerfectSquare 121 1))
