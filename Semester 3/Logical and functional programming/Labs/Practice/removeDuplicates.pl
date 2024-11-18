% write a predicate to remove all duplicates 
% 
% remove(l1l2..ln) = [], n = 0
% 					 l1 U remove(l2..ln), n > 0 and l1 not in l2..ln
% 					 remove(l2..ln), n > 0 and l1 in l2..ln

remove([], []).
remove([H|T], R):-
    notInList(T, H),
    remove(T, Rp),
    R=[H|Rp].
remove([H|T], R):-
    \+notInList(T, H),
    remove(T, R).

notInList([], _).
notInList([H|T], X):-
    H=\=X,
    notInList(T, X).
