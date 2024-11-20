insert(X, [], [X]).
insert(X, [H|T], [X, H|T]):-
    X < H.
insert(X, [H|T], [H|T]):-
    X=:=H.
insert(X, [H|T], [H|R]):-
    X > H,
    insert(X, T, R).

sortList([], []).
sortList([Head|Tail], SortedList):-
    sortList(Tail, SortedTail),
    insert(Head, SortedTail, SortedList).

sortSublists([], []).
sortSublists([Head|Tail], [SortedHead|SortedSublists]):-
    is_list(Head),
    sortList(Head, SortedHead),
    sortSublists(Tail, SortedSublists).
sortSublists([Head|Tail], [Head|SortedSublists]):-
    \+ is_list(Head),
  	sortSublists(Tail, SortedSublists).