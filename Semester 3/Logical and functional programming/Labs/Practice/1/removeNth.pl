%remove(X, L, N)

remove([], _, []).
remove([H|T], N, R):-
    N > 0,
    N1 is N - 1,
    remove(T, N1, RP),
    R=[H|RP].
remove([_|T], 0, T). 
