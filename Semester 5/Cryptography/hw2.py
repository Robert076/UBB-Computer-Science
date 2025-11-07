# 4
from math import gcd

def generators_Zn(n):
    return [g for g in range(1, n) if gcd(g, n) == 1]

n = 15
print("Generators of Z_{}: {}".format(n, generators_Zn(n)))
