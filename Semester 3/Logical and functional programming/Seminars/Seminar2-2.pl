sumC([], C, C).
sumC([H|T], C, S):-
    CNew is C+H,
    sumC(T, CNew, S).
mainSumC(L, R):-sumC(L, 0, R).
