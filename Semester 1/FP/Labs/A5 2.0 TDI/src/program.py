#
# Write the implementation for A5 in this file
#

# 
# Write below this comment 
# Functions to deal with complex numbers -- list representation
# -> There should be no print or input statements in this section 
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#

def manageUserInputForList(listOfComplexNumbers):
    userInput = int(input("Enter your choice: "))
    choiceReadList = 1
    choiceDisplayList = 2
    choiceDisplayLongestSequenceModulusLessThanTen = 3
    choiceDisplayLongestSequenceThatIsPalindrome = 4

    if userInput == choiceReadList:
        readList(listOfComplexNumbers)
    elif userInput == choiceDisplayList:
        displayList(listOfComplexNumbers)
    elif userInput == choiceDisplayLongestSequenceModulusLessThanTen:
        left, right = figureLongestSequenceWithModulusLessThanTenList(listOfComplexNumbers)
        displayLongestSequenceWithModulusLessThanTenList(left, right, listOfComplexNumbers)
    elif userInput == choiceDisplayLongestSequenceThatIsPalindrome:
        left, right = figureLongestSequenceWithIncreasingModulusList(listOfComplexNumbers)
        displayLongestSequenceWithIncreasingModulusList(left, right, listOfComplexNumbers)
    elif userInput == "5":
        exit()
    else:
        print("Invalid input. Please try again.")

#
# Write below this comment 
# Functions to deal with complex numbers -- dict representation
# -> There should be no print or input statements in this section 
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#

def manageUserInputForDict(listOfComplexNumbers):
    userInput = int(input("Enter your choice: "))
    choiceReadDict = 1
    choiceDisplayDict = 2
    choiceDisplayLongestSequenceModulusLessThanTen = 3
    choiceDisplayLongestSequenceWithIncreasingModulus = 4

    if userInput == choiceReadDict:
        readDict(listOfComplexNumbers)
    elif userInput == choiceDisplayDict:
        displayDict(listOfComplexNumbers)
    elif userInput == choiceDisplayLongestSequenceModulusLessThanTen:
        left, right = figureLongestSequenceWithModulusLessThanTenDict(listOfComplexNumbers)
        displayLongestSequenceWithModulusLessThanTenDict(left, right, listOfComplexNumbers)
    elif userInput == choiceDisplayLongestSequenceWithIncreasingModulus:
        left, right = figureLongestSequenceWithIncreasingModulusDict(listOfComplexNumbers)
        displayLongestSequenceWithIncreasingModulusDict(left, right, listOfComplexNumbers)
    elif userInput == "5":
        exit()

#
# Write below this comment 
# Functions that deal with subarray/subsequence properties
# -> There should be no print or input statements in this section 
# -> Each function should do one thing only
# -> Functions communicate using input parameters and their return values
#

def figureLongestSequenceWithIncreasingModulusDict(listOfComplexNumbers):
    right = 0
    left = 0
    currentLength = 0
    longestLength = 0
    leftFinal = 0
    rightFinal = 0
    lastModulus = 0
    for i in range(0, len(listOfComplexNumbers)):
        modulus = (listOfComplexNumbers[i]["real"] ** 2 + listOfComplexNumbers[i]["imaginary"] ** 2) ** 0.5
        if modulus > lastModulus:
            currentLength += 1
            right = i
        else:
            currentLength = 0
            left = i + 1
        if currentLength > longestLength:
            leftFinal = left
            rightFinal = right
        lastModulus = modulus
    return leftFinal, rightFinal

def figureLongestSequenceWithModulusLessThanTenDict(listOfComplexNumbers):
    right = 0
    left = 0
    currentLength = 0
    longestLength = 0
    leftFinal = 0
    rightFinal = 0
    for i in range(0, len(listOfComplexNumbers)):
        modulus = (listOfComplexNumbers[i]["real"] ** 2 + listOfComplexNumbers[i]["imaginary"] ** 2) ** 0.5
        if modulus < 10:
            currentLength += 1
            right = i
        else:
            currentLength = 0
            left = i + 1
        if currentLength > longestLength:
            leftFinal = left
            rightFinal = right
    return leftFinal, rightFinal

def figureLongestSequenceWithIncreasingModulusList(listOfComplexNumbers):
    right = 0
    left = 0
    currentLength = 0
    longestLength = 0
    leftFinal = 0
    rightFinal = 0
    lastModulus = 0
    for i in range(0, len(listOfComplexNumbers)):
        modulus = (listOfComplexNumbers[i][0] ** 2 + listOfComplexNumbers[i][1] ** 2) ** 0.5
        if modulus > lastModulus:
            currentLength += 1
            right = i
        else:
            currentLength = 0
            left = i + 1
        if currentLength > longestLength:
            leftFinal = left
            rightFinal = right
        lastModulus = modulus
    return leftFinal, rightFinal

def figureLongestSequenceWithModulusLessThanTenList(listOfComplexNumbers):
    right = 0
    left = 0
    currentLength = 0
    longestLength = 0
    leftFinal = 0
    rightFinal = 0
    for i in range(0, len(listOfComplexNumbers)):
        modulus = (listOfComplexNumbers[i][0] ** 2 + listOfComplexNumbers[i][1] ** 2) ** 0.5
        if modulus < 10:
            currentLength += 1
            right = i
        else:
            currentLength = 0
            left = i + 1
        if currentLength > longestLength:
            leftFinal = left
            rightFinal = right
    return leftFinal, rightFinal

#
# Write below this comment 
# UI section
# Write all functions that have input or print statements here
# Ideally, this section should not contain any calculations relevant to program functionalities
#

def readDict(listOfComplexNumbers):
    print("Please enter the number of complex numbers: ")
    numberOfComplexNumbers = int(input())
    for i in range(numberOfComplexNumbers):
        print("Please enter the real part of the complex number: ")
        realPart = int(input())
        print("Please enter the imaginary part of the complex number: ")
        imaginaryPart = int(input())
        listOfComplexNumbers.append({"real": realPart, "imaginary": imaginaryPart}) # the "real" key is the real part, the "imaginary" key is the imaginary part

def displayDict(listOfComplexNumbers):
    print(listOfComplexNumbers)

def readList(listOfComplexNumbers):
    print("Please enter the number of complex numbers: ")
    numberOfComplexNumbers = int(input())
    for i in range(numberOfComplexNumbers):
        print("Please enter the real part of the complex number: ")
        realPart = int(input())
        print("Please enter the imaginary part of the complex number: ")
        imaginaryPart = int(input())
        listOfComplexNumbers.append([realPart, imaginaryPart]) # the first index is the real part, the second index is the imaginary part

def displayList(listOfComplexNumbers):
    print(listOfComplexNumbers)

def printMenuForList():
    print("1. Read a list of complex numbers")
    print("2. Display the list of numbers")
    print("3. Display the longest sequence of numbers with the property: modulus of all numbers < 10")
    print("4. Display the longest sequence that has increasing modulus")
    print("5. Exit")

def printMenuForDict():
    print("1. Read a dictionary of complex numbers")
    print("2. Display the dictionary of numbers")
    print("3. Display the longest sequence of numbers with the property: modulus of all numbers < 10")
    print("4. Display the longest sequence that has increasing modulus")
    print("5. Exit")

def displayLongestSequenceWithIncreasingModulusDict(leftFinal, rightFinal, listOfComplexNumbers):
    for i in range(leftFinal, rightFinal + 1):
        print(listOfComplexNumbers[i])

def displayLongestSequenceWithModulusLessThanTenDict(leftFinal, rightFinal, listOfComplexNumbers):
    for i in range(leftFinal, rightFinal + 1):
        print(listOfComplexNumbers[i])

def displayLongestSequenceWithModulusLessThanTenList(leftFinal, rightFinal, listOfComplexNumbers):
    for i in range(leftFinal, rightFinal + 1):
        print(listOfComplexNumbers[i])
def displayLongestSequenceWithIncreasingModulusList(leftFinal, rightFinal, listOfComplexNumbers):
    for i in range(leftFinal, rightFinal + 1):
        print(listOfComplexNumbers[i])

def startAppForList():
    listOfComplexNumbers = []
    try:
        while True:
            printMenuForList()
            manageUserInputForList(listOfComplexNumbers)
    except KeyboardInterrupt:
        pass

def startAppForDict():
    listOfComplexNumbers = []
    try:
        while True:
            printMenuForDict()
            manageUserInputForDict(listOfComplexNumbers)
    except KeyboardInterrupt:
        pass

if __name__ == "__main__":
    #startAppForList()
    startAppForDict()

