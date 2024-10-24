/* Remove all occurences of given atom from the list */
removeAtom(_, [], []).   % Base case: Empty list.

removeAtom(Atom, [Atom | Tail], Result) :-   % Case 1: Head matches the atom.
    removeAtom(Atom, Tail, Result).

removeAtom(Atom, [Head | Tail], [Head | Result]) :-   % Case 2: Head does not match.
    Atom \= Head,
    removeAtom(Atom, Tail, Result).

removeAtomMain(Atom, List, Result) :- 
    removeAtom(Atom, List, Result).

trace,(   removeAtomMain('@', ['@', '%', '$', 'mar', '@', 'pere'], R))

/* Pairs problem */

count([], _, 0).
count([H|T], H, N) :- count(T, H, N1), N is N1 + 1.
count([H|T], X, N) :- H \= X, count(T, X, N).

pair([], []).
pair([H|T], [[H,N]|Rest]) :- count([H|T], H, N), remove([H|T], H, NewT), pair(NewT, Rest).

remove([], _, []).
remove([H|T], H, T1) :- remove(T, H, T1).
remove([H|T], X, [H|T1]) :- H \= X, remove(T, X, T1).

% Main predicate
pairMain(List) :-
    pair(List, Pairs).               % Output the result



trace,(   pairMain([1, 2, 1, 1, 3]))