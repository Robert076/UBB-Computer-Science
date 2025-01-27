pkg load statistics
% Data
data = [7, 7, 4, 5, 9, 9, 4, 12, 8, 1, 8, 7, 3, 13, 2, 1, 17, 7, ...
        12, 5, 6, 2, 1, 13, 14, 10, 2, 4, 9, 11, 3, 5, 12, 6, 10, 7];
n = length(data);
alpha = 0.05; % for 95% confidence interval
mean_data = mean(data);

% Part (a): Known sigma
sigma = 5;
z_critical = norminv(1 - alpha/2);
CI_a = [mean_data - z_critical * sigma / sqrt(n), ...
        mean_data + z_critical * sigma / sqrt(n)];

% Part (b): Unknown sigma
sample_std = std(data);
t_critical = tinv(1 - alpha/2, n - 1);
CI_b = [mean_data - t_critical * sample_std / sqrt(n), ...
        mean_data + t_critical * sample_std / sqrt(n)];

% Display Results
disp('Part (a): CI for mean with known sigma:');
disp(CI_a);

disp('Part (b): CI for mean with unknown sigma:');
disp(CI_b);

