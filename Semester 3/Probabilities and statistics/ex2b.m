clear all
pkg load statistics;
p=0.3
N=100000
n=20

U=rand(n,N);
X=sum(U<p);

k=0:n;
px=binopdf(k,n,p);
U_X=unique(X);

n_X=hist(X,length(U_X));
rel_freq=n_X/N
clf
plot(U_X,rel_freq,"*",k,px,"ro")
legend("sim","bino")
