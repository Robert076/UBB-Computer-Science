%insert(X, L, N, R).

insert(X, [], _, [X]).
insert(X, [H|T], N, [H|R]):-
    N>0,
    N1 is N-1,
    insert(X, T, N1, R).
insert(X, L, 0, [X|L]).