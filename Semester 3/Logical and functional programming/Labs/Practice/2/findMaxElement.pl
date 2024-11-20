maxElem(L, R):-
  maxElem(L, 0, R).
maxElem([], CurrMax, CurrMax).
maxElem([H|T], CurrMax, R):-
  H > CurrMax,
  maxElem(T, H, R).
maxElem([_|T], CurrMax, R):-
  maxElem(T, CurrMax, R).
