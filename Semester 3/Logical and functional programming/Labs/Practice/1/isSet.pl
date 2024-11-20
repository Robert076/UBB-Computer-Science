isSet([]).
isSet([H|T]):-
    notInList(H, T),
    isSet(T).

notInList(_, []).
notInList(X, [H|T]):-
    X=\=H,
    notInList(X, T).

main(L):-
    isSet(L).