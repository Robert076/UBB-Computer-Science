import math

def pollard_p_minus_1(n, B=10000):
    a = 2  

    for j in range(2, B + 1):
        a = pow(a, j, n)

        d = math.gcd(a - 1, n)

        if 1 < d < n:
            return d  

    return None  

n = 10403  # 101 * 103

factor = pollard_p_minus_1(n)
print("Factor gasit:", factor)

factor2 = pollard_p_minus_1(n, B=5000)
print("Factor gasit cu B=5000:", factor2)
