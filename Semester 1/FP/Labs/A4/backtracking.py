def getUserInputAndMakeSureItsANumber():
    userInput = input()
    if userInput.isnumeric():
        if 1 <= int(userInput) <= 2:
            return int(userInput)
    else:
        exit("Please enter a number")


def chooseAlgorithmToDetermineAllScoreOptions():
    print("Enter the number representing the option you want to chose from the menu:")
    print("1. Iterative algorithm")
    print("2. Recursive algorithm")
    user_input = getUserInputAndMakeSureItsANumber()
    if user_input == 1:
        iterativeAlgorithmToDetermineAllScoreOptions([1, 'x', 2], [])
    else:
        recursiveAlgorithmToDetermineAllScoreOptions([1, 'x', 2], [], 0, 0)


def iterativeAlgorithmToDetermineAllScoreOptions(optionsToChooseList, scoreOptionsList):
    scoreOption1Counter = 0
    for i in optionsToChooseList:
        if i == 1:
            scoreOption1Counter += 1
        for j in optionsToChooseList:
            if j == 1:
                scoreOption1Counter += 1
            for k in optionsToChooseList:
                if k == 1:
                    scoreOption1Counter += 1
                for l in optionsToChooseList:
                    if l == 1:
                        scoreOption1Counter += 1
                    if scoreOption1Counter <= 2:
                        if l != 'x':
                            scoreOptionsList.extend([i, j, k, l])
                            print(scoreOptionsList)
                            scoreOptionsList.clear()
                    if l == 1:
                        scoreOption1Counter -= 1
                if k == 1:
                    scoreOption1Counter -= 1
            if j == 1:
                scoreOption1Counter -= 1
        if i == 1:
            scoreOption1Counter -= 1


def recursiveAlgorithmToDetermineAllScoreOptions(optionsToChooseList, scoreOptionsList, currentScoreOptionsListElement, scoreOption1Counter):
    for index in range(0, len(optionsToChooseList)):
        scoreOptionsList.append(optionsToChooseList[index])
        if scoreOptionsList[currentScoreOptionsListElement] == 1:
            scoreOption1Counter += 1
        if scoreOption1Counter <= 2 and currentScoreOptionsListElement < 4:
            if currentScoreOptionsListElement == 3 and scoreOptionsList[currentScoreOptionsListElement] != 'x':
                print(scoreOptionsList)
            recursiveAlgorithmToDetermineAllScoreOptions(optionsToChooseList, scoreOptionsList, currentScoreOptionsListElement + 1, scoreOption1Counter)
        if scoreOptionsList[currentScoreOptionsListElement] == 1:
            scoreOption1Counter -= 1
        scoreOptionsList.pop()


chooseAlgorithmToDetermineAllScoreOptions()

