% Substitute all occurences of an element of a list with a given list

% replace(A, E, B, R) A-> list1, E-> element, B-> list2, R-> result

replace([], _, _, []).
replace([H|T], E, B, R):-
    H=:=E,
    replace(T, E, B, Rp),
    R=[B|Rp].
replace([H|T], E, B, [H|R]):-
    H=\=E,
    replace(T, E, B, R).

