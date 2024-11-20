% the difference between two sets
setDifference([], _, []).
setDifference([H|T], S2, Diff):-
    notInList(H, S2),
    setDifference(T, S2, DiffT),
    Diff = [H|DiffT].
setDifference([H|T], S2, Diff):-
    \+ notInList(H, S2),
    setDifference(T, S2, Diff).

notInList(_, []).
notInList(X, [H|T]):-
    X =\= H,
    notInList(X, T).
    