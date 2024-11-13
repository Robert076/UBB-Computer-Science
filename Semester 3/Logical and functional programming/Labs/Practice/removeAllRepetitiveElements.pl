remove([], _, []).
remove([H|T], O, ANS):-
    nrOcc(H, O, R),
    R=:=1,
    remove(T, O, ANSb),
    ANS=[H|ANSb].
remove([H|T], O, ANS):-
    nrOcc(H, O, R),
    R=\=1,
    remove(T, O, ANS).
nrOcc(_, [], 0).
nrOcc(X, [H|T], R):-
    X=:=H,
    nrOcc(X, T, Rp),
    R is Rp + 1.
nrOcc(X, [H|T], R):-
    X=\=H,
    nrOcc(X, T, R).