% sort a list with removing double values


insert(L, E, R):-
    insert(L, R, 0, E).

insert([], [], _, _).
insert([H|T], [H,E|R], CurPos, E):-
    CurPos=:=1,
    CurPos1 is CurPos + 1,
    insert(T, R, CurPos1, E).
insert([H|T], [H,E|R], CurPos, E):-
    CurPos=:=3,
    CurPos1 is CurPos + 1,
    insert(T, R, CurPos1, E).
insert([H|T], [H,E|R], CurPos, E):-
    CurPos=:=7,
    CurPos1 is CurPos + 1,
    insert(T, R, CurPos1, E).
insert([H|T], [H|R], CurPos, E):-
    CurPos1 is CurPos + 1,
    insert(T, R, CurPos1, E).

insertInSublists([], []).
insertInSublists([H1,H2|T], [H1,Res|R]):-
    is_list(H2),
    insert(H2, H1, Res),
    insertInSublists(T, R).
insertInSublists([H|T], [H|R]):-
    insertInSublists(T, R).
