% Remove X from list
% remove(X, L)

remove(_, [], []).
remove(X, [H|T], R):-
    X=:=H,
    remove(X, T, R).
remove(X, [H|T], R):-
 	X=\=H,
    remove(X, T, RP),
    R=[H|RP].
