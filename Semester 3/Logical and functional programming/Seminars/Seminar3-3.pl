% Given a list of numbers and sublists of numbers,
% substitute each list in which the sum of elements
% is odd with the first element of that sublist
% ex: [1,2,[3,4,5],5,[7],9,[1,2,4]]
% => [1,2,[3,4,5],5,7,9,1]
%					{ [], n = 0
%	prob(l1l2..ln) ={ l11 U prob(l2..ln), l1 is list sum(l1)%2=1, l1=l11 l12 .. l1n
%					{ l1 U prob(l2..ln), l1 elem or(l1 list and 
%
% prob(L-list, R-result)
% flowmodel(i, o)

prob([],[]).
prob([H|T], [HL|R]):-
    is_list(H),
    sum(H, Sum),
    Sum mod 2 =:= 1, !,
    H=[HL|_],
    prob(T,R).
prob([H|T], [H|R]):-
    prob(T,R).