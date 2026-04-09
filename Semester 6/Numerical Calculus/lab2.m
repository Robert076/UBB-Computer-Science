% =========================================================
% EXERCISE 2: Numerical Calculus - Sine Function
% =========================================================

% ---------------------------------------------------------
% Part 2a: Graphing sin(x) and its Taylor Polynomials
% ---------------------------------------------------------
fprintf('\n--- Part 2a: Generating Plot ---\n');

% Define the interval x from -pi to pi with 100 points
x = linspace(-pi, pi, 100);

% Define the original function sin(x)
f = sin(x);

% Define the Taylor polynomials manually using formula (4)
T3 = x - (x.^3)/factorial(3);
T5 = x - (x.^3)/factorial(3) + (x.^5)/factorial(5);

% Create the plot
figure;
plot(x, f, 'k', 'LineWidth', 2); % Change 'k' to 'c' if using a pure black background
hold on;
plot(x, T3, 'r', 'LineWidth', 1.5);
plot(x, T5, 'g', 'LineWidth', 1.5);
hold off;

% Add labels, legend, and grid with white text for dark themes
title('Function sin(x) and its Taylor Polynomials', 'Color', 'w');
xlabel('x', 'Color', 'w');
ylabel('y', 'Color', 'w');

% Create the legend and set text to white
lgd = legend('sin(x)', 'T_3(x)', 'T_5(x)', 'Location', 'south');
set(lgd, 'TextColor', 'w'); 
grid on;

% Change the axes (box, tick marks, grid lines) to white
set(gca, 'XColor', 'w', 'YColor', 'w');

% ---------------------------------------------------------
% Part 2b: Approximating sin(pi/5) with 5 correct decimals
% ---------------------------------------------------------
fprintf('\n--- Part 2b: Approximating sin(pi/5) ---\n');

x_val = pi/5;
exact_val = sin(x_val);

% Threshold for 5 correct decimals
tolerance = 0.5 * 10^-5;
approx_val = 0;

% Loop strictly following formula (4) from the PDF where degree = 2n - 1
for n = 1:10
    
    % Calculate the current term: (-1)^(n-1) * x^(2n-1) / (2n-1)!
    degree = 2*n - 1;
    term = ((-1)^(n-1)) * (x_val^degree) / factorial(degree);
    
    % Add term to approximation
    approx_val = approx_val + term;
    
    % Calculate the theoretical remainder bound based on PDF formula (4)
    % The maximum possible error is bounded by the next terms max value
    remainder_bound = (x_val^(2*n + 1)) / factorial(2*n + 1);
    current_error = abs(exact_val - approx_val);
    
    % Print out every step so we can see exactly why n=3 is rejected
    fprintf('Checking n = %d (Degree %d): Approx = %.6f | Error = %.8f\n', n, degree, approx_val, current_error);
    
    % The careful check: Both the actual error and theoretical bound must be < tolerance
    if (current_error < tolerance) && (remainder_bound < tolerance) && (round(approx_val, 5) == round(exact_val, 5))
        fprintf('\n>>> SUCCESS! Target precision safely reached at n = %d <<<\n', n);
        fprintf('Degree used:          %d\n', degree);
        fprintf('Exact sin(pi/5):      %.6f\n', exact_val);
        fprintf('Final Approximation:  %.6f\n', approx_val);
        break;
    end
end

% ---------------------------------------------------------
% Part 2c: The large x phenomenon (x = 10*pi/3)
% ---------------------------------------------------------
fprintf('\n--- Part 2c: The 10*pi/3 Phenomenon ---\n');

large_x = 10*pi/3;

% The solution: reduce the angle to the [-pi, pi] interval
% mod(x + pi, 2*pi) - pi neatly wraps any angle into [-pi, pi]
reduced_x = mod(large_x + pi, 2*pi) - pi;

fprintf('Original large angle: %.4f radians\n', large_x);
fprintf('Reduced angle:        %.4f radians\n', reduced_x);
fprintf('sin(large_x):         %.4f\n', sin(large_x));
fprintf('sin(reduced_x):       %.4f\n', sin(reduced_x));
fprintf('Use the reduced angle in the Taylor series for stable convergence!\n');
