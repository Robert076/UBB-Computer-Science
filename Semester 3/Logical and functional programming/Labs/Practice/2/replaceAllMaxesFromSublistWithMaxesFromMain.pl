% Replace all occurences of an element from a list with
% another element e

replace([], _, _, []).
replace([H|T], H, E, [E|R]):-
    replace(T, H, E, R).
replace([H|T], X, E, [H|R]):-
    replace(T, X, E, R).

getMax(L, R):-
    getMax(L, 0, R).
getMax([], CurMax, CurMax).
getMax([H|T], CurMax, R):-
    is_list(H),
    getMax(T, CurMax, R).
getMax([H|T], CurMax, R):-
    H =< CurMax,
    getMax(T, CurMax, R).
getMax([H|T], _, R):-
    getMax(T, H, R).
    
solve(L, R):-
    getMax(L, Max),
    solve(L, Max, R).
solve([], _, []).
solve([H|T], Max, [New|R]):-
    is_list(H),
    getMax(H, MaxFromList),
    replace(H, Max, MaxFromList, New),
    solve(T, Max, R).
solve([H|T], Max, [H|R]):-
      solve(T, Max, R).
