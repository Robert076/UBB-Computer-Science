mmul(_, [], []).
mmul(K, [H|T], [HK|TK]):-
    HK is K * H,
    mmul(K, T, TK).
// multiplies every element of a list with given constant"
