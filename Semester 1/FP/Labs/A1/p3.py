# Solve the problem from the third set here 13

'''

Problem 13
13. Determine the `n-th`  element of the sequence `1,2,3,2,5,2,3,7,2,3,2,5,...` obtained from the sequence of natural
numbers by replacing composed numbers with their prime divisors, without memorizing the elements of the sequence.

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

def nthInSequence(index: int):
    '''
    :param index: The index of the number we want to determine
    :return: 
    '''
    num = 1 # The first number in the sequence is 1
    count = 1 # What index are we currently on
    if count == index: # Count = 1 case
        print(1)
    while count < index:
        num = num + 1 # Checking all numbers one by one until we reach the nth one
        if(isPrime(num)):
            count = count + 1
            if count == index: # We found the nth element
                print(num)
                break
        else:
            for d in range(2, num): # Current number not prime, we go for its prime divisors
                    if num % d == 0: # We found a divisor
                        if isPrime(d): # We found a prime divisor
                            count = count + 1
                        if count == index: # We found the nth element
                            print(d)
                            exit()


nthInSequence(12)