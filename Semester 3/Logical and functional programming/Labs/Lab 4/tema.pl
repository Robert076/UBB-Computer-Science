% Generate all subsets of a list
subsets([], []).
subsets([H|T], [H|R]) :-
    subsets(T, R).
subsets([_|T], R) :-
    subsets(T, R).

% Check if three points are collinear
are_collinear((X1, Y1), (X2, Y2), (X3, Y3)) :-
    (X2 - X1) * (Y3 - Y1) =:= (Y2 - Y1) * (X3 - X1).

% Check if all points in a subset are collinear
collinear([]).
collinear([_]).
collinear([_, _]).
collinear([P1, P2, P3|Rest]) :-
    are_collinear(P1, P2, P3),
    collinear([P1, P3|Rest]).


    findall(S, (subsets([(0,0), (1,1), (2,2), (1,0)], S), S \= [], collinear(S)), Result).


