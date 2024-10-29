% Chi-squared distribution

% P(X <= 0)
n_chi = input("Enter n for the Chi-squared distribution: ");
p_less_equal_zero = chi2cdf(0, n_chi);
fprintf('P(X <= 0) for X^2(%d) is %.4f\n', n_chi, p_less_equal_zero)

% P(X >= 0)
p_greater_equal_zero = 1 - p_less_equal_zero;
fprintf('P(X >= 0) for X^2(%d) is %.4f\n', n_chi, p_greater_equal_zero)

% P(-1 <= X <= 1)
p_x_leq_1 = chi2cdf(1, n_chi);
p_x_leq_neg1 = 0; % Chi-squared values are always non-negative
p_between_neg1_and_1 = p_x_leq_1 - p_x_leq_neg1; % This will just be P(X <= 1)
fprintf('P(-1 <= X <= 1) for X^2(%d) is %.4f\n', n_chi, p_between_neg1_and_1);

% xalpha
alpha = input("Enter the value of alpha (0 < alpha < 1): ");
xalpha = chi2inv(alpha, n_chi);
fprintf('Xalpha is %.4f\n', xalpha)

% xbeta
beta = input("Enter the value of beta (0 < beta < 1): ");
xbeta = chi2inv(1 - beta, n_chi);
fprintf('Xbeta is %.4f\n', xbeta)

