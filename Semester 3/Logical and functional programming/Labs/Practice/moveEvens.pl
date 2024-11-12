%
%	Move all even numbers from a list into a new one
%
%				{ [], L = []
%	move(L, R)= { [H] U move(T, R) if L = [H|T] and H%2 == 0
%				{ move(T, R) if L = [H|T] and H%2 != 0


move([], []).

move([H|T], [H|R]):-
    H mod 2 =:= 0,
    move(T, R).

move([H|T], R):-
    H mod 2 =:= 1,
    move(T, R).

moveMain(L, R):-
    move(L, R).