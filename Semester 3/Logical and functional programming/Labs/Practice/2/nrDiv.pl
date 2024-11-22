

nrDiv(N, R):-
    nrDiv(N, 0, 1, R).

nrDiv(N, CurCount, CurDiv, CurCount):-
    CurDiv > N.
nrDiv(N, CurCount, CurDiv, R):-
    CurDiv =< N,
    IsDiv is N mod CurDiv,
    IsDiv=:=0,
	CurCount1 is CurCount + 1,
    CurDiv1 is CurDiv + 1,
    nrDiv(N, CurCount1, CurDiv1, R).
nrDiv(N, CurCount, CurDiv, R):-
    CurDiv =< N,
    IsDiv is N mod CurDiv,
    IsDiv=\=0,
    CurDiv1 is CurDiv + 1,
    nrDiv(N, CurCount, CurDiv1, R).