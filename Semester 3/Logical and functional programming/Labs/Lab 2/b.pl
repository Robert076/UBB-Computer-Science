%
%						{ [], if L=[]
%	sort_sublists(L) =  { [Head|sort_sublists(Tail)] if Head is a number
%						{ [sort_list(Head)|sort_sublists(Tail)] if Head is a list
%	flow(input,output)
%
%	sort_list(L) = 	{ []
%					{ insert(Head, sort_list(Tail)) if L=[Head|Tail]
%	flow(input,output)
%
%	insert(X, L) =  { [X], if L = []
%				    { [X|L], if L=[Y|Tail] and X<=Y
%				    { [Y|insert(X,Tail)], if L=[Y|Tail] and X>Y
%   flow(input, input, output)
%



sort_sublists([], []).

sort_sublists([Head | Tail], [Head | SortedTail]) :-
    number(Head),
    sort_sublists(Tail, SortedTail).

sort_sublists([Head | Tail], [SortedHead | SortedTail]) :-
    is_list(Head),
    sort_list(Head, SortedHead),        
    sort_sublists(Tail, SortedTail).    

sort_list([], []).
sort_list([Head | Tail], SortedList) :-
    sort_list(Tail, SortedTail),
    insert(Head, SortedTail, SortedList).

insert(X, [], [X]).
insert(X, [Y | Tail], [X, Y | Tail]) :- X =< Y.
insert(X, [Y | Tail], [Y | ResultTail]) :- X > Y, insert(X, Tail, ResultTail).

sort_sublists_main([], []).

sort_sublists_main(List, SortedList) :-
    sort_sublists(List, SortedList).
