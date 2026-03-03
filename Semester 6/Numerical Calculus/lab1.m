% Define the interval x from -3 to 3 with 100 points
x = linspace(-3, 3, 100);

% Define the original function e^x
f = exp(x);

% Define the Taylor polynomials manually (vectorized)
T1 = 1 + x;
T2 = 1 + x + (x.^2)/2;
T3 = 1 + x + (x.^2)/2 + (x.^3)/6;
T4 = 1 + x + (x.^2)/2 + (x.^3)/6 + (x.^4)/24;

% Create the plot
figure;
plot(x, f, 'k', 'LineWidth', 2); 
hold on;
plot(x, T1, 'r', 'LineWidth', 1.5);
plot(x, T2, 'g', 'LineWidth', 1.5);
plot(x, T3, 'b', 'LineWidth', 1.5);
plot(x, T4, 'm', 'LineWidth', 1.5);
hold off;

% Add labels, legend, and grid
title('Function e^x and its Taylor Polynomials');
xlabel('x');
ylabel('y');
legend('e^x', 'T_1(x)', 'T_2(x)', 'T_3(x)', 'T_4(x)', 'Location', 'northwest');
grid on;

% Define the vector of terms from k = 0 to 10
k = 0:10;

% Calculate the sum of 1/k!
e_approx = sum(1 ./ factorial(k));

% Display the result with 6 decimal places
fprintf('Approximation of e: %.6f\n', e_approx);

% Part 1b: Approximate e with 6 correct decimals
fprintf('\n--- Part 1b: Approximating e ---\n');

% Define the vector of terms from k = 0 to 10
k = 0:10;

% Calculate the Taylor series approximation for e^1
% This computes the sum of 1/k!
e_approx = sum(1 ./ factorial(k));

% Display the result formatted to 6 decimal places
fprintf('Approximation of e: %.6f\n', e_approx);

% Calculate the exact value of e built into Octave
e_exact = exp(1);

% Calculate the absolute error between exact and approximated
abs_error = abs(e_exact - e_approx);

% Define the threshold for 6 correct decimals
threshold = 0.5 * 10^-6;

% Display the high-precision values to see what's happening under the hood
fprintf('\n--- Verifying the Error ---\n');
fprintf('Exact e:      %.10f\n', e_exact);
fprintf('Approx e:     %.10f\n', e_approx);
fprintf('Actual Error: %.10f\n', abs_error);
fprintf('Threshold:    %.10f\n', threshold);

% Perform the logical check
if abs_error < threshold
    fprintf('\nSUCCESS: The actual error is LESS than the threshold!\n');
else
    fprintf('\nFAILED: The error exceeds the threshold.\n');
end
