import random


def startApplication(numbersList):
    print("Choose an option")
    print("Option 1 : Generate a list of n random natural numbers")
    print("Option 2 : Sort the list using the exchange sort algorithm")
    print("Option 3 : Sort the list using the heap sort algorithm")
    print("Option 4 : Exit program")
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
        generateListOfRandomNumbers()
    elif userInput == 2 or userInput == 3:
        stepsCount = getUserInputForNumbers()
        if userInput == 2:
            useExchangeSortAlgorithm(numbersList, stepsCount)
        elif userInput == 3:
            useHeapSortAlgorithm(numbersList, stepsCount)
    elif userInput == 4:
        exitApp()
    else:
        inputIsIncorrect()

def generateListOfRandomNumbers():
    print("Please enter the number that corresponds to the list size: ")
    numberOfListElements = getUserInputForNumbers()
    numbersList = random.sample(range(0, 100), int(numberOfListElements))
    print("Your list is : ", numbersList)
    startApplication(numbersList)

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