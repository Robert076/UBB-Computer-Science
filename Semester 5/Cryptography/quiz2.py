import math

# Given values
n = 6931
def f(x):
    return (x * x + 1) % n

# generate x_i
x = [2]  # x0

# compute enough xi values (we need up to x20 at most)
for _ in range(1, 21):
    x.append(f(x[-1]))

# perform Pollard rho iteration scheme
print("Pollard rho iterations:\n")

k = 1
while True:
    x1 = x[2*k - 1]
    x2 = x[2*k]
    ref = x[k]         # x_k
    d = math.gcd(abs(x2 - ref), n)

    print(f"Row {k}:  x_{2*k-1} = {x1},  x_{2*k} = {x2},  gcd(|{x2} - {ref}|, n) = {d}")

    if 1 < d < n:
        print("\nNon-trivial factor found:", d)
        print("The other factor is:", n // d)
        break

    k += 1

