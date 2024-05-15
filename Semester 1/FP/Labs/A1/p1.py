# Solve the problem from the first set here 1

'''

Problem 1
1. Generate the first prime number larger than a given natural number `n`.

'''

def isPrime(userInput: int):
    '''
    :param userInput: Number for which we check primality
    :return: 
    '''
    if userInput == 0 or userInput == 1:
        return 0 # Get rid of input = 0 or input = 1 case
    elif userInput > 2 and userInput % 2 == 0:
        return 0 # Removing all even numbers
    for d in range(3, userInput, 2):
        if d != 0:
            if userInput % d == 0:
                return 0 # Removing all odd non-prime numbers
    return 1 # Input is prime

def largerPrimeThanN(userInput: int):
    '''
    :param userInput: The number we want the first larger prime for
    :return: 
    '''
    num = userInput + 1 # Start checking numbers from input + 1
    while(isPrime(num) == 0):
        num = num + 1 # If the current number isn't prime, we increment it by one
    return num # Return first number that meets the requirements


print(largerPrimeThanN(7)) # Print result