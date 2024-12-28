(defun checkNum(num)
    (cond
    ((< num 0) "Negative")
    ((> num 0) "Positive")
    ((= num 0) "Zero")))

(print(checkNum 0))