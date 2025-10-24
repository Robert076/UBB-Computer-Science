import time

def gcd_subtraction(a, b):
    if a == 0:
        return b
    if b == 0:
        return a
    while a != b:
        if a > b:
            a -= b
        else:
            b -= a
    return a

def gcd_modulo(a, b):
    while b:
        r = a % b
        a = b
        b = r
    return a

def gcd_binary(a, b):
    if a == 0:
        return b
    if b == 0:
        return a
    shift = 0
    while ((a | b) & 1) == 0:
        a >>= 1
        b >>= 1
        shift += 1
    while (a & 1) == 0:
        a >>= 1
    while b != 0:
        while (b & 1) == 0:
            b >>= 1
        if a > b:
            a, b = b, a
        b -= a
    return a << shift

test_pairs = [
    (12, 0),
    (1234, 5678),
    (98765, 43210),
    (1000000, 250000),
    (123456789, 987654321),
    (999999937, 999999929),
    (2**20 - 1, 2**15 - 1),
    (10**10, 10**5),
    (10**50, 10**25),         
    (10**100, 10**50),        
]

for a, b in test_pairs:
    print(f"\Pair: ({a}, {b})")

    start = time.perf_counter()
    gcd_subtraction(a, b)
    print("  Subtraction:", time.perf_counter() - start)

    start = time.perf_counter()
    gcd_modulo(a, b)
    print("  Modulo:", time.perf_counter() - start)

    start = time.perf_counter()
    gcd_binary(a, b)
    print("  Binary:", time.perf_counter() - start)
