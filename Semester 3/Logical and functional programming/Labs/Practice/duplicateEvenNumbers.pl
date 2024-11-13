%duplicate every even number
%dup(L)

dup([], []).
dup([H|T], R):-
    H mod 2 =:= 0,
    dup(T, RP),
    R = [H, H | RP].
dup([H|T], R):-
    \+ H mod 2 =:= 0,
    dup(T, RP),
    R = [H | RP].

