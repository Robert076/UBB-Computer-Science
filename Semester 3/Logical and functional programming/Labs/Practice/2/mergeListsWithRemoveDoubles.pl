% merge lists with remove doubles


merge([], [], []).
merge(L1, [], L1).
merge([], L2, L2).
merge([H|T1], [H|T2], [H|R]):-
    merge(T1, T2, R).
merge([H1|T1], [H2|T2], [H1|R]):-
    H1 < H2,
    merge(T1, [H2|T2], R).
merge([H1|T1], [H2|T2], [H2|R]):-
    merge([H1|T1], T2, R).
