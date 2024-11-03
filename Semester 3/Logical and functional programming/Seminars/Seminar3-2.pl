% Remove sublists of a heterogeneous list
% process(l1l2..ln) = [], if n = 0
%                     process(l2..ln), if l1 is a list
%					  l1 U process(l2..ln), if l1 is not a list

process([], []).
process([H|T], R):-
    is_list(H), !, % ! is not a negation, it means if the first element is a list dont try the other branch
    process(T, R).
process([H|T],[H|R]):-
	\+ is_list(H), % or not(is_list(H))
	process(T,R).

process([1,2,[3,4,5],5,[7],9,[]], R).