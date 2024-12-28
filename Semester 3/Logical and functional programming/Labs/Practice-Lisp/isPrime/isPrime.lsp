(defun isPrime(x d)
    (cond
        ((< x 2) "X is not a Prime")
        ((and (evenp x)(\= x 2)) "X is not a Prime")
        ((>= d x) "X is prime")
        ((= (mod x d) 0) "X is not a Prime")
        (t (isPrime x (+ d 1)))))

(print (isPrime 18 2))
