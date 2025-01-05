(defun checkExistence (lst x)
  (cond
    ((null lst) nil)  ; Base case: empty list, return nil
    ((listp (car lst)) 
     (or (checkExistence (car lst) x)  ; Check nested list (car)
         (checkExistence (cdr lst) x))) ; Check the rest of the list (cdr)
    ((= x (car lst)) "Da ma da")  ; Found the value
    (t (checkExistence (cdr lst) x))))  ; Continue with the rest of the list
(print(checkExistence '(1 2 3) 2) )