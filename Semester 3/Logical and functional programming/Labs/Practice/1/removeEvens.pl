removeEven([], []). 

removeEven([H|T], Result):-
    H mod 2 =\= 1,
    removeEven(T, Result).

removeEven([H|T], [H|Result]):-
    H mod 2 =\= 0,
    removeEven(T, Result).

removeEvenMain(L, R):-
    removeEven(L, R).

