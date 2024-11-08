%				
%	sort_list(L) = { [], if L = []
%				   { insert(Head, sort_list(Tail)) if L = [Head | Tail]
%	FLOW(input, output)
%
%				    { [X], if L = []
%	insert(X, L) =  { [X|L], if L=[Y∣Tail] and X<=Y
%				    { [Y|insert(X,Tail)], if L=[Y|Tail] and X > Y
%	FLOW(input, input, output)
%
%


sort_list([], []).

sort_list([Head | Tail], SortedList) :-
    sort_list(Tail, SortedTail),
    insert(Head, SortedTail, SortedList).

insert(X, [], [X]).

insert(X, [Y | Tail], [X, Y | Tail]) :-
    X =< Y.

insert(X, [Y | Tail], [Y | ResultTail]) :-
    X > Y,
    insert(X, Tail, ResultTail).
 