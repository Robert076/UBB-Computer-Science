% backtracking
% [1, 2, 3] n - nr elements, 2^n subsets
% [], [1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]
% subsets(l1l2..ln)=[]
%					l1 U subsets(l2..ln), n > 0
%					subsets(l2..ln), n > 0


subsets([], []).
subsets([H|T], [H|R]):-
    subsets(T, R).
subsets([_|T], R):-
    subsets(T, R).

% findall(R, subsets([1, 2, 3], R), X)%




% [1, 2, 3] -> 2, 1, 3
% n! permutations
% insertEl(e, l1..ln) = e U l1..ln, n >= 0
% 						l1 U insertEl(e, l2..ln), n > 0
% insertEl(E-elem, L-lost, R-list) (i, i, o)

insertEl(E, L, [E|L]).
insertEl(E, [H|T], [H|R]):-
    insertEl(E, T, R).

% perm(l1l2..ln) = [], if n = 0
%				   insertEl(l1, perm(l2..ln)), n > 0

perm([], []).
perm([H|T], R):-
    perm(T, P),
    insertEl(H, P, R).

%findall(R, perm([1, 2, 3, 4], R), X).





% [1, 2, 3], k = 2 => [1, 3] [2,3], [1, 2]
% comb(l1l2..ln, k) = l1, if k = 1, n >= 1
%					  comb(l2..ln, k), if k >= 1, n > 1
%					  l1 U comb(l2..ln, k - 1), if k > 1.

comb([H|_], 1, [H]).
comb([_|T], K, R):-
    K >= 1,
    comb(T, K, R).
comb([H|T], K, [H|R]):-
    K > 1,
    K1 is K - 1,
    comb(T, K1, R).

%findall(R, comb([1, 2, 3], 2, R), X).



% [1, 2, 3], k = 2 => [1, 2], [2, 1], [1, 3], [3, 1], [2, 3], [3, 2]
% arr(l1l2..ln, k) = l1, if k = 1, n >= 1
%					 arr(l2..ln, k), if k >= 1, n > 1
%					 insertElement(l1, arr(l2..ln, k - 1)), if k > 1.
arr([H|_], 1, [H]).
arr([_|T], K, R):-
    K >= 1,
    arr(T, K , R).
arr([H|T], K, R):-
    K > 1,
    K1 is K - 1,
    arr(T, K1, A),
    insertEl(H, A, R).

