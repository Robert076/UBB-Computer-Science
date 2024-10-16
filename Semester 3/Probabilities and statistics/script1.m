pkg load statistics
n=input("Give nb. of trials n=");% n - positive integer
p=input("Give probability of success p=");% p in [0, 1]

x=0:n;% this is an array that contains the numbers from 0 to n (0, 1, 2,...)

px=binopdf(x, n, p);

plot(x, px, '*')

