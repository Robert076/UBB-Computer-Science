# Solve the problem from the second set here 11

'''

Problem 11
11. The numbers `n1` and `n2` have the property `P` if their writing in base 10 uses the same digits
(e.g. `2113 and 323121`). Determine whether two given natural numbers have property `P`.

'''

def checkP():
    '''
    :return: The function returns 1 if the two numbers have property 'P', 0 otherwise
    '''
    n = int(input("Enter first number: ")) # Read first number
    m = int(input("Enter second number: ")) # Read second number

    nArray = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0] # Array that stores 1 whether a digit is found in N, 0 otherwise (index 0 is digit 1, and so on)
    mArray = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0] # Array that stores 1 whether a digit is found in M, 0 otherwise (index 0 is digit 1, and so on)

    while n:
        currentDigit = int(n % 10) # Copy current digit from N
        if(nArray[currentDigit] == 0): # Found new digit
            nArray[currentDigit] = 1 # Marking in the array the fact that we found a digit that previously was not there
        n = int(n / 10) # Cut last digit

    while m:
        currentDigit = int(m % 10) # Copy current digit from M
        if(mArray[currentDigit] == 0): # Found new digit
            mArray[currentDigit] = 1 # Marking in the array the fact that we found a digit that previously was not there
        m = int(m / 10) # Cut last digit

    sameDigits = 1 # We assume the two numbers are made of the same digit
    for i in range(0, 10):
        if(nArray[i] != mArray[i]):
            sameDigits = 0 # The two numbers have different digits
            break

    if(sameDigits):
        print("The two numbers have 'P' property")
    else:
        print("The two numbers don't have 'P' property")

checkP()