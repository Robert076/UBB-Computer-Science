lucky(N):- 
    N=:=4.
lucky(N):-
    N=:=7.
lucky(N):-
    UC is N mod 10,
    UC=:=7,
    N1 is N div 10,
    lucky(N1).
lucky(N):-
    UC is N mod 10,
    UC=:=4,
    N1 is N div 10,
    lucky(N1).

