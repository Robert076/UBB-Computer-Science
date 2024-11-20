% removeCons(L, R, lastRem, didRem)


removeCons(L, R):-
    removeCons(L, R, 0, 0).
removeCons([], [], _, _).
removeCons([H1,H2|T], R, _, _):-
    H1N is H1 + 1,
    H2=:= H1N,
    LastRem1 is H2,
    DidRem1 is 1,
    removeCons(T, R, LastRem1, DidRem1).
removeCons([H1,H2|T], [H2|R], LastRem, DidRem):-
    H1N is H1 + 1,
    H2=\=H1N,
    DidRem=:=1,
    LastRem1 is LastRem + 1,
    H1=:=LastRem1,
    LastRem2 is LastRem1,
    DidRem1 is 0,
    removeCons([H2|T], R, LastRem2, DidRem1).
removeCons([H|T], R, LastRem, DidRem):-
    H1 is LastRem + 1,
    DidRem=:=1,
    H=:=H1,
    removeCons(T, R, LastRem, DidRem).
removeCons([H|T], [H|R], LastRem, DidRem):-
    DidRem=:=1,
    DidRem1 is 0,
    removeCons(T, R, LastRem, DidRem1).
removeCons([H|T], [H|R], LastRem, DidRem):-
    removeCons(T, R, LastRem, DidRem).
    

