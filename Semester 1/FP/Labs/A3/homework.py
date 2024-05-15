import random
from timeit import default_timer


def startApplication(numbersList):
    print("Choose an option")
    print("Option 1 : Generate a list of n random natural numbers")
    print("Option 2 : Sort the list using the exchange sort algorithm")
    print("Option 3 : Sort the list using the heap sort algorithm")
    print("Option 4 : See how the algorithms will perform in the best case scenario")
    print("Option 5 : See how the algorithms will perform in an average case scenario")
    print("Option 6 : See how the algorithms will perform in the worst case scenario")
    print("Option 7 : Exit program")
    userInput = getUserInputForNumbers()
    filterUserChoice(numbersList, userInput)


def exitApp():
    print("Thank you for using my app.")
    exit()


def inputIsIncorrect():
    print("Please use a valid input!")
    exit()


def filterUserChoice(numbersList : list, userInput: int):
    if userInput == 1:
        getUserInputForTheSizeOfTheList()
    elif userInput == 2 or userInput == 3:
        stepsCount = getUserInputForNumbers()
        if userInput == 2:
            useExchangeSortAlgorithm(numbersList, stepsCount)
        elif userInput == 3:
            useHeapSortAlgorithm(numbersList, stepsCount)
    elif userInput == 4 or userInput == 5 or userInput == 6:
        userAlgorithmChoice = redirectUserToChooseTheAlgorithmHeWantsToObserve()
        if userInput == 4:
            generateNumbersListForBestCaseScenario(500, userAlgorithmChoice, 1, userInput)
        if userInput == 5:
            generateNumbersListForAverageCaseScenario(500, userAlgorithmChoice, 1, userInput)
        if userInput == 6:
            generateNumbersListForWorstCaseScenario(500, userAlgorithmChoice, 1, userInput)
    elif userInput == 7:
        exitApp()
    else:
        inputIsIncorrect()


def determineTimeComplexityForAlgorithm(numbersList, numberOfElements, userAlgorithmChoice, stepsCount, userInput):
    if stepsCount <= 5:
        timerStart = 0
        timerEnd = 0
        if userAlgorithmChoice == 1:
            timerStart = default_timer()
            useExchangeSortAlgorithm(numbersList, -1)
            timerEnd = default_timer()
        else:
            timerStart = default_timer()
            useHeapSortAlgorithm(numbersList, -1)
            timerEnd = default_timer()
        totalTime = timerEnd - timerStart
        totalTimeString = "{:.8f}".format(totalTime)
        print("Sorting the list with the ", numberOfElements, " took ", totalTimeString, " seconds")
        if userInput == 4:
            generateNumbersListForBestCaseScenario(numberOfElements * 2, userAlgorithmChoice, stepsCount + 1, userInput)
        elif userInput == 5:
            generateNumbersListForAverageCaseScenario(numberOfElements * 2, userAlgorithmChoice, stepsCount + 1, userInput)
        elif userInput == 6:
            generateNumbersListForWorstCaseScenario(numberOfElements * 2, userAlgorithmChoice, stepsCount + 1, userInput)


def generateNumbersListForBestCaseScenario(numberOfElements, userAlgorithmChoice, stepsCount, userInput):
    numbersList = generateListOfRandomNumbers(numberOfElements)
    numbersList.sort()
    determineTimeComplexityForAlgorithm(numbersList, numberOfElements, userAlgorithmChoice, stepsCount, userInput)


def generateNumbersListForAverageCaseScenario(numberOfElements, userAlgorithmChoice, stepsCount, userInput):
    numbersList = generateListOfRandomNumbers(numberOfElements)
    determineTimeComplexityForAlgorithm(numbersList, numberOfElements, userAlgorithmChoice, stepsCount, userInput)


def generateNumbersListForWorstCaseScenario(numberOfElements, userAlgorithmChoice, stepsCount, userInput):
    numbersList = generateListOfRandomNumbers(numberOfElements)
    numbersList.sort()
    numbersList.reverse()
    determineTimeComplexityForAlgorithm(numbersList, numberOfElements, userAlgorithmChoice, stepsCount, userInput)


def redirectUserToChooseTheAlgorithmHeWantsToObserve():
    print("Please enter the number corresponding to the algorithm you want to see performing :")
    print("1 : Exchange sort")
    print("2 : Heap sort algorithm")
    userInput = getUserInputForNumbers()
    if userInput > 2 or userInput < 1:
        inputIsIncorrect()
    return userInput


def getUserInputForTheSizeOfTheList():
    print("Please enter the number that corresponds to the list size: ")
    numberOfListElements = getUserInputForNumbers()
    numbersList = generateListOfRandomNumbers(numberOfListElements)
    print("Your list is : ", numbersList)
    startApplication(numbersList)

def generateListOfRandomNumbers(numberOfListElements):
    numbersList = []
    for i in range(0, numberOfListElements):
        currentGeneratedRandomNumber = random.randint(0, 100)
        numbersList.append(currentGeneratedRandomNumber)
    return numbersList


def getUserInputForNumbers():
    userInput = input()
    if userInput.isnumeric():
        return int(userInput)
    else:
        inputIsIncorrect()


def checkIfWeCanPrintTheList(currentStep : int, stepsCount : int, numbersList : list):
    if currentStep % stepsCount == 0:
        print("The list is currently : ", numbersList)


def useExchangeSortAlgorithm(numbersList : list, stepsCount : int):
    currentStep = int(0)
    for index1 in range(0, len(numbersList) - 1):
        for index2 in range(index1 + 1, len(numbersList)):
            if(numbersList[index1] > numbersList[index2]):
                (numbersList[index1], numbersList[index2]) = (numbersList[index2], numbersList[index1])
                currentStep = currentStep + 1
                if stepsCount != -1:
                    checkIfWeCanPrintTheList(currentStep, stepsCount, numbersList)


def useHeapSortAlgorithm(numbersList : list, stepsCount : int):
    currentStep = 0
    heapSort(numbersList, currentStep, stepsCount)


def heapify(numbersList : list, numberOfElements : int , i, currentStep, stepsCount):
    largest = i
    left = 2 * i + 1
    right = 2 * i + 2
    if left < numberOfElements and numbersList[i] < numbersList[left]:
        largest = left
    if right < numberOfElements and numbersList[largest] < numbersList[right]:
         largest = right
    if largest != i:
        currentStep = currentStep + 1
        (numbersList[i], numbersList[largest]) = (numbersList[largest], numbersList[i])
        if stepsCount != -1:
            checkIfWeCanPrintTheList(currentStep, stepsCount, numbersList)
        heapify(numbersList, numberOfElements, largest, currentStep, stepsCount)


def heapSort(numbersList, currentStep, stepsCount):
    numberOfElements = len(numbersList)
    for i in range(numberOfElements // 2 - 1, -1, -1):
        heapify(numbersList, numberOfElements, i, currentStep, stepsCount)
    for i in range(numberOfElements - 1, 0, -1):
        (numbersList[i], numbersList[0]) = (numbersList[0], numbersList[i])
        heapify(numbersList, i, 0, currentStep, stepsCount)


startApplication([])