add([], E, [E]).
add([H|T], E, [H|R]):-
    add(T, E, R).


# ADD AN ELEMENT TO THE END OF THE LIST
