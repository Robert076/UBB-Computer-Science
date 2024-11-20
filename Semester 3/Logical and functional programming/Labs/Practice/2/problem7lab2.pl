maxElem(L, R):-
    maxElem(L, 0, R).

maxElem([], CurMax, CurMax).
maxElem([H|T], CurMax, R):-
    H > CurMax,
    maxElem(T, H, R).
maxElem([_|T], CurMax, R):-
    maxElem(T, CurMax, R).

append([], E, [E]).
append([H|T], E, [H|R]):-
    append(T, E, R).

maxPos(L, R):-
    maxElem(L, MaxElem),
    maxPos(L, [], R, MaxElem, 0).

maxPos([], R, R, _, _).
maxPos([H|T], MaxPosList, R, MaxElem, Count):-
    H=:=MaxElem,
    Count1 is Count + 1,
    append(MaxPosList, Count1, MaxPosList1),
    maxPos(T, MaxPosList1, R, MaxElem, Count1).
maxPos([_|T], MaxPosList, R, MaxElem, Count):-
    Count1 is Count + 1,
    maxPos(T, MaxPosList, R, MaxElem, Count1).
