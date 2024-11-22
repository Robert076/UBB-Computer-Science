% check if a number is prime
prime(X):-
    divisorsCount(X, 1, R),
    R=:=2.

divisorsCount(X, CurDiv, 0):-
    CurDiv > X.
divisorsCount(X, CurDiv, R):-
	CurDiv =< X,
    X mod CurDiv =:= 0,
    divisorsCount(X, CurDiv + 1, RP),
    R is RP + 1.
divisorsCount(X, CurDiv, R):-
    CurDiv =< X,
    X mod CurDiv =\= 0,
    divisorsCount(X, CurDiv + 1, R).
    
