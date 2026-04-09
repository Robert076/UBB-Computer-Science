% =========================================================
% EXERCISE 1: Numerical Calculus - Taylor Polynomials
% =========================================================

% ---------------------------------------------------------
% Part 1a: Graphing e^x and its Taylor Polynomials
% ---------------------------------------------------------
fprintf('--- Part 1a: Generating Plot ---\n');

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
plot(x, f, 'k', 'LineWidth', 2); % e^x line (change 'k' to 'c' if background is pure black)
hold on;
plot(x, T1, 'r', 'LineWidth', 1.5);
plot(x, T2, 'g', 'LineWidth', 1.5);
plot(x, T3, 'b', 'LineWidth', 1.5);
plot(x, T4, 'm', 'LineWidth', 1.5);
hold off;

% Add labels, legend, and grid with white text
title('Function e^x and its Taylor Polynomials', 'Color', 'w');
xlabel('x', 'Color', 'w');
ylabel('y', 'Color', 'w');

% Create the legend and set text to white
lgd = legend('e^x', 'T_1(x)', 'T_2(x)', 'T_3(x)', 'T_4(x)', 'Location', 'northwest');
set(lgd, 'TextColor', 'w'); 

grid on;

% Change the axes (box, tick marks, grid lines) to white
set(gca, 'XColor', 'w', 'YColor', 'w');
% set(gcf, 'Color', 'k'); % Uncomment this to force a black figure background


% ---------------------------------------------------------
% Part 1b: Approximating e with 6 correct decimals
% ---------------------------------------------------------
fprintf('\n--- Part 1b: Approximating e ---\n');

% The exact value of e built into Octave
e_exact = exp(1);

% The threshold for 6 correct decimals (0.5 * 10^-6)
tolerance = 0.5 * 10^-6;

% Start with an approximation of 0
e_approx = 0;

% Loop through possible degrees from 0 to 10
for k = 0:10
    
    % Add the next term of the MacLaurin series (1/k!)
    e_approx = e_approx + 1 / factorial(k);
    
    % Calculate the current absolute error
    current_error = abs(e_exact - e_approx);
    
    % Check if we hit our target precision
    if current_error < tolerance
        fprintf('Target precision reached at degree k = %d\n', k);
        fprintf('Current Error: %.10f (Must be < %.10f)\n', current_error, tolerance);
        fprintf('Approximation of e: %.6f\n', e_approx);
        break; % Target met, exit the loop
    end
end
