from random import randint


def naiveVersionToFindSubarrayWithSumK():
    numberOfListElements = getUserInputForNumberOfListElements()
    listElements = getUserInputForListElements(numberOfListElements)
    sumWeAreSearchingFor = int(getUserInputForSumWeAreSearchinFor())
    subsetWithDesiredSum = []

    def isSubsetWithSum(index, currentSum):
        if currentSum == 0:
            return True
        if index == 0:
            return False
        withoutCurrent = isSubsetWithSum(index - 1, currentSum)
        if withoutCurrent:
            return True
        withCurrent = isSubsetWithSum(index - 1, currentSum - listElements[index - 1])
        if withCurrent:
            subsetWithDesiredSum.append(listElements[index - 1])
            return True
        return False

    if isSubsetWithSum(numberOfListElements, sumWeAreSearchingFor):
        printListAsSet(subsetWithDesiredSum)
    else:
        theGivenSumCanNotBeComputedAsASubset()

    # 2 ^ n


def optimizedVersionToFindGivenSum(listElements, sumWeAreSearchingFor, numberOfListElements):
        numberOfListElements = len(listElements)

        checkIfSumCanBeFormedWithSetElements = [[False for _ in range(sumWeAreSearchingFor + 1)] for _ in
                                                range(numberOfListElements + 1)]

        for i in range(numberOfListElements + 1):
            checkIfSumCanBeFormedWithSetElements[i][0] = True
        print("The matrix used to generate subset: ")
        for i in range(1, numberOfListElements + 1):
            for currentSum in range(1, sumWeAreSearchingFor + 1):
                if currentSum < listElements[i - 1]:
                    checkIfSumCanBeFormedWithSetElements[i][currentSum] = \
                        checkIfSumCanBeFormedWithSetElements[i - 1][currentSum]
                else:
                    checkIfSumCanBeFormedWithSetElements[i][currentSum] = \
                        checkIfSumCanBeFormedWithSetElements[i - 1][currentSum] or \
                        checkIfSumCanBeFormedWithSetElements[i - 1][currentSum - listElements[i - 1]]
                    printListAsMatrix(checkIfSumCanBeFormedWithSetElements)

        if not checkIfSumCanBeFormedWithSetElements[numberOfListElements][sumWeAreSearchingFor]:
            return theGivenSumCanNotBeComputedAsASubset()

        subsetWithDesiredSum = []
        i, currentSum = numberOfListElements, sumWeAreSearchingFor
        while i > 0 and currentSum > 0:
            if checkIfSumCanBeFormedWithSetElements[i][currentSum] and not \
                    checkIfSumCanBeFormedWithSetElements[i - 1][currentSum]:
                subsetWithDesiredSum.append(listElements[i - 1])
                currentSum -= listElements[i - 1]
            i -= 1
        subsetWithDesiredSum.reverse()
        printListAsSet(subsetWithDesiredSum)

    # n ^ 2

def printListAsMatrix(matrix):
    for row in matrix:
        print(row)

def printListAsSet(inputList):
     print(inputList)

def theGivenSumCanNotBeComputedAsASubset():
    print("The given sum cannot be computed")

def displayMenuForUserToChooseTheAlgorithm():
    print("Choose which algorithm you want to use in order to find subarray with sum k: ")
    print("1. Naive algorithm")
    print("2. Optimized algorithm (DP)")
    print("3. Exit program")
    userInput = int(input("> "))
    filterUserInputAndMakeSureItIsValid(userInput)


def filterUserInputAndMakeSureItIsValid(userInput):
    if userInput == 1:
        naiveVersionToFindSubarrayWithSumK()
    elif userInput == 2:
        numberOfListElements = getUserInputForNumberOfListElements()
        listElements = getUserInputForListElements(numberOfListElements)
        sumWeAreSearchingFor = int(getUserInputForSumWeAreSearchinFor())
        memoizeTab = [[-1 for i in range(2000)] for j in range(2000)]
        optimizedVersionToFindGivenSum(listElements, sumWeAreSearchingFor, numberOfListElements)
    elif userInput == 3:
        exit()
    else:
        print("Invalid input")

def getUserInputForNumberOfListElements():
    numberOfListElements = int(input("Please enter the size of your list : "))
    return numberOfListElements

def getUserInputForSumWeAreSearchinFor():
    sumWeAreSearchingFor = input("Please enter the sum you want to search for in the array : ")
    return sumWeAreSearchingFor

def getUserInputForListElements(numberOfListElements):
    listElements = []
    for i in range(numberOfListElements):
        listElements.append(int(input("Number " + str(i) + " : ")))
    return listElements

while True:
    displayMenuForUserToChooseTheAlgorithm()
