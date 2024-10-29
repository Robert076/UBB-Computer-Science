% Student's t distribution

% P(X <= 0)
n_t = input("Enter n for the Student's distribution: ");
p_less_equal_zero = tcdf(0, n_t);
fprintf('P(X <= 0) for T(%d) is %.4f\n', n_t, p_less_equal_zero)

% P(X >= 0)
p_greater_equal_zero = 1 - p_less_equal_zero;
fprintf('P(X >= 0) for T(%d) is %.4f\n', n_t, p_greater_equal_zero)

% P(-1 <= X <= 1)
p_x_leq_1 = tcdf(1, n_t);
p_x_leq_neg1 = tcdf(-1, n_t);
p_between_neg1_and_1 = p_x_leq_1 - p_x_leq_neg1;
fprintf('P(-1 <= X <= 1) for T(%d) is %.4f\n', n_t, p_between_neg1_and_1);

% xalpha
alpha = input("Enter the value of alpha (0 < alpha < 1): ");
xalpha = tinv(alpha, n_t);
fprintf('Xalpha is %.4f\n', xalpha)

% xbeta
beta = input("Enter the value of beta (0 < beta < 1): ");
xbeta = tinv(1 - beta, n_t);
fprintf('Xbeta is %.4f\n', xbeta)

