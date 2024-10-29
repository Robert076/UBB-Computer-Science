% Fisher's F distribution

% Input degrees of freedom
m = input("Enter degrees of freedom for the numerator (m): ");
n = input("Enter degrees of freedom for the denominator (n): ");

% P(X <= 0)
p_less_equal_zero = fcdf(0, m, n);
fprintf('P(X <= 0) for F(%d, %d) is %.4f\n', m, n, p_less_equal_zero)

% P(X >= 0)
p_greater_equal_zero = 1 - p_less_equal_zero;
fprintf('P(X >= 0) for F(%d, %d) is %.4f\n', m, n, p_greater_equal_zero)

% P(-1 <= X <= 1)
p_x_leq_1 = fcdf(1, m, n);
p_x_leq_neg1 = 0; % Fisher's F distribution values are always non-negative
p_between_neg1_and_1 = p_x_leq_1 - p_x_leq_neg1; % This will just be P(X <= 1)
fprintf('P(-1 <= X <= 1) for F(%d, %d) is %.4f\n', m, n, p_between_neg1_and_1);

% xalpha
alpha = input("Enter the value of alpha (0 < alpha < 1): ");
xalpha = finv(alpha, m, n);
fprintf('Xalpha is %.4f\n', xalpha)

% xbeta
beta = input("Enter the value of beta (0 < beta < 1): ");
xbeta = finv(1 - beta, m, n);
fprintf('Xbeta is %.4f\n', xbeta)

