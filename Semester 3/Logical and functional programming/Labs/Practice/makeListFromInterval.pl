% write a predicate to create a list with numbers in the interval [n, m]

sol(M, M, [M]).
sol(N, M, L):-
    N =< M,
    N1 is N + 1,
    sol(N1, M, Lp),
    L=[N|Lp].