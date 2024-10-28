evenProd([], 1).
evenProd([H|T], R):-
    H mod 2 =:= 0,
    evenProd(T, RT),
    R is RT * H.
evenProd([H|T], R):-
    H mod 2 =\= 0,
    evenProd(T, R).

evenProdMain([], -1).
evenProdMain(L, P):-
    evenProd(L, P).


#----------------#

