union([], S, S).
union([H|T], S, R):-
    notInList(H, S),
    union(T, S, Rp),
    R = [H|Rp].
union([H|T], S, R):-
    \+ notInList(H, S),
    union(T, S, R).

notInList(_, []).
notInList(X, [H|T]):-
    X=\=H,
    notInList(X,T).