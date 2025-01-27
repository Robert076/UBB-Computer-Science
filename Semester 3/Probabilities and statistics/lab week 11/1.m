% past assumptions
% vs present assumptions

% statistical parameter = some known value <- null hyp - what was previously known
% statistical parameter > < or diff from the same
% known value as above <- (will be called <-) the alternative hyp

% statistical parameters that can be analysed
% a pop mean, a pop variance
% ratio of 2 pop variances, diff of 2 pop meansq
% "on average" in problem 1 tells us its pop mean
X = [7,7,4,5,9,9,...
4,12,8,1,8,7,...
3,13,2,1,17,7,...
12,5,6,2,1,13,...
14,10,2,4,9,11,...
3,5,12,6,10,7];

n = length(X)
alpha = input("Please enter significance level: ")
% sign level is a problem

% a)
% H0:   m = 8.5 (null hypothesis, sys is eff)
% H1:  m < 8.5 (alternative hyp, sys is not eff)

% this is a z-test (test a mean when sigma is known)
% left tail z test because of "<" for the pop mean when sigma is known

sigma = 5;
m0 = 8.5; % observed val of the mean

% help ztest take 2nd and 5th line
[h, p, ci, zobs] = ztest(X,m0,sigma,"alpha",alpha, "tail","left")
% the first string means hey octave pay attention what follows next is my significance level
% what follows after "tail" is our desired test

z = norminv(alpha, 0, 1);
RR = [-inf z];
if h==1 % if h = 1 reject H0 and if h = 0 do not reject H0
    print("The value of h is %d. The null hyp is rejected\n", h)
    print("The data suggests that the standard is not met")
else
    print("The value of h is %d. The null hyp is not rejected\n", h)
    print("The data suggests that the standard is met")
endif

% we must prin the rejection region for this test

print("The rejection region for this test is (%4.3f, %4.3f).\n", RR)
print("The observed value for the test statistic is %4.3f.\n", zobs)
print("The p value of our test is %4.3f.\n", p)
