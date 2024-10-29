% Normal distribution

% P(X <= 0)

mu = input("Enter mean of distribution: ")
sigma = input("Enter the value of sigma: ")
p_less_equal_zero = normcdf(0, mu, sigma);
fprintf('P(X <= 0) for N(%.2f, %.2f) is %.4f\n', mu, sigma, p_less_equal_zero)


% P(X >= 0)

p_greater_equal_zero = 1 - p_less_equal_zero;
fprintf('P(X >= 0) for N(%.2f, %.2f) is %.4f\n', mu, sigma, p_greater_equal_zero)

% P(-1 <= X <= 1)

p_x_leq_1 = normcdf(1, mu, sigma);
p_x_leq_neg1 = normcdf(-1, mu, sigma);
p_between_neg1_and_1 = p_x_leq_1 - p_x_leq_neg1;
fprintf('P(-1 <= X <= 1) for N(%.2f, %.2f) is %.4f\n', mu, sigma, p_between_neg1_and_1);

% xalpha
alpha = input("Enter the value of alpha (0 < alpha < 1): ");
xalpha = norminv(alpha, mu, sigma);
fprintf('Xalpha is %d\n', xalpha)

% xbeta
beta = input("Enter the value of beta (0 < beta < 1): ");
xbeta = norminv(1 - beta, mu, sigma);
fprintf('Xbeta is %d\n', xbeta)
