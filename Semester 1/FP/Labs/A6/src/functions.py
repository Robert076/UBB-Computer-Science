#
# The program's functions are implemented here. There is no user interaction in this file, therefore no input/print statements. Functions here
# communicate via function parameters, the return statement and raising of exceptions. 
#

from ui import *

def manageListTransactions(userInput, listOfTransactions):
    apartment = getApartmentForPrint(userInput)
    if apartment == ">":
        expense = getExpenseForPrint(userInput)
        for i in listOfTransactions:
            if int(i["amount"]) > int(expense):
                print(i)
    elif apartment == "<":
        expense = getExpenseForPrint(userInput)
        for i in listOfTransactions:
            if int(i["amount"]) < int(expense):
                print(i)
    elif apartment == "=":
        expense = getExpenseForPrint(userInput)
        for i in listOfTransactions:
            if int(i["amount"]) == int(expense):
                print(i)
    elif apartment.isnumeric():
        for i in listOfTransactions:
            if int(i["apartment"]) == int(apartment):
                print(i)
    elif apartment == "":
        for i in listOfTransactions:
            print(i)


def getApartmentNumberFromTransactionAdd(userInput):
    number = ""
    i = 4
    while userInput[i] != " " and i < len(userInput):
        number += userInput[i]
        i += 1
        if i == len(userInput):
            break
    return number

def getTypeFromTransactionAdd(userInput):
    type = ""
    i = 4
    while userInput[i] != " " and i < len(userInput):
        i += 1
        if i == len(userInput):
            break
    if i < len(userInput) - 1:
        i += 1
        while userInput[i] != " " and i < len(userInput):
            type += userInput[i]
            i += 1
            if i == len(userInput):
                break

        return type
    else:
        raise Exception("Not enough parameters for add command")

def getAmountFromTransactionAdd(userInput):
    amount = ""
    i = 4
    while userInput[i] != " " and i < len(userInput):
        i += 1
        if i == len(userInput):
            break
    if i < len(userInput) - 1:
        i += 1
        while userInput[i] != " " and i < len(userInput):
            i += 1
            if i == len(userInput):
                break
        if i < len(userInput) - 1:
            i += 1
            while i < len(userInput):
                amount += userInput[i]
                i += 1
                if i == len(userInput):
                    break
        else:
            raise Exception("Not enough parameters for add command")

        return amount
    else:
        raise Exception("Not enough parameters for add command")

def manageNewTransaction(userInput, listOfTransactions, stackOfOperations):
    if len(userInput) < 9:
        raise Exception("Not enough parameters for add command")

    apartment = getApartmentNumberFromTransactionAdd(userInput)
    type = getTypeFromTransactionAdd(userInput)
    amount = getAmountFromTransactionAdd(userInput)

    stackOfOperations.append({"command": "add", "apartment": apartment, "amount": amount, "type": type})

    listOfTransactions.append({"apartment": apartment, "type": type, "amount": amount})

def getFirstApartmentNumberFromTransactionRemove(userInput):
    number = ""
    i = 7
    while i < len(userInput) and userInput[i] != " ":
        number += userInput[i]
        i += 1
        if i == len(userInput):
            break

    return number

def getSecondApartmentNumberFromTransactionRemove(userInput):
    number = ""
    i = 7
    while i < len(userInput) and userInput[i] != " ":
        i += 1
        if i == len(userInput):
            break
        if i + 1 < len(userInput) and i + 2 < len(userInput):
            if userInput[i + 1] == "t" and userInput[i + 2] == "o":
                i += 4
                if i < len(userInput):
                    while i < len(userInput):
                        number += userInput[i]
                        i += 1
                        if i == len(userInput):
                            break
    return number

def getApartmentFromTransactionReplace(userInput):
    apartment = ""
    i = 8
    while i < len(userInput) and userInput[i] != " ":
        apartment += userInput[i]
        i += 1
        if i == len(userInput):
            break
    return apartment

def getTypeFromTransactionReplace(userInput):
    type = ""
    i = 8
    while i < len(userInput) and userInput[i] != " ":
        i += 1
        if i == len(userInput):
            break
    if i < len(userInput) - 1:
        i += 1
        while i < len(userInput) and userInput[i] != " ":
            type += userInput[i]
            i += 1
            if i == len(userInput):
                break

        return type
    else:
        raise Exception("Not enough parameters for replace command")

def getAmountFromTransactionReplace(userInput):
    amount = ""
    i = 8
    while i < len(userInput) and userInput[i] != " ":
        i += 1
        if i == len(userInput):
            break
    if i < len(userInput) - 1:
        i += 1
        while i < len(userInput) and userInput[i] != " ":
            i += 1
            if i == len(userInput):
                break
        if i < len(userInput) - 1:
            i += 1
            while userInput[i] == "w" or userInput[i] == "i" or userInput[i] == "t" or userInput[i] == "h" or userInput[i] == " ":
                i += 1
                if i == len(userInput):
                    break
            while i < len(userInput):
                amount += userInput[i]
                i += 1
                if i == len(userInput):
                    break
        else:
            raise Exception("Not enough parameters for replace command")

        return amount
    else:
        raise Exception("Not enough parameters for replace command")

def manageReplaceTransaction(userInput, listOfTransactions, stackOfOperations):
    apartment = getApartmentFromTransactionReplace(userInput)
    type = getTypeFromTransactionReplace(userInput)
    amount = getAmountFromTransactionReplace(userInput)

    if apartment == "" or type == "" or amount == "":
        raise Exception("Not enough parameters for replace command")

    for transaction in listOfTransactions:
        if transaction["apartment"] == apartment:
            if transaction["type"] == type:
                stackOfOperations.append({"command": "replace", "apartment": apartment, "amount": transaction["amount"], "type": type})
                transaction["amount"] = amount
def manageRemoveTransaction(userInput, listOfTransactions, stackOfOperations):
    firstNumber = getFirstApartmentNumberFromTransactionRemove(userInput)
    secondNumber = getSecondApartmentNumberFromTransactionRemove(userInput)
    removeType = "removeEven"

    if stackOfOperations and stackOfOperations[-1]["command"] == "removeEven":
        removeType = "removeOdd"

    if secondNumber == "" and firstNumber.isnumeric():
        for transaction in reversed(listOfTransactions):
            if int(transaction["apartment"]) == int(firstNumber):
                stackOfOperations.append({"command": removeType, "apartment": transaction["apartment"], "amount": transaction["amount"], "type": transaction["type"]})
                listOfTransactions.remove(transaction)
    elif secondNumber != "":
        for transaction in reversed(listOfTransactions):
            if int(transaction["apartment"]) >= int(firstNumber) and int(transaction["apartment"]) <= int(secondNumber):
                stackOfOperations.append({"command": removeType, "apartment": transaction["apartment"], "amount": transaction["amount"], "type": transaction["type"]})
                listOfTransactions.remove(transaction)
    elif secondNumber == "" and not firstNumber.isnumeric():
        for transaction in reversed(listOfTransactions):
            if transaction["type"] == firstNumber:
                stackOfOperations.append({"command": removeType, "apartment": transaction["apartment"], "amount": transaction["amount"], "type": transaction["type"]})
                listOfTransactions.remove(transaction)


def manageUndo(listOfTransactions, stackOfOperations):
    if len(stackOfOperations) > 0:
        if stackOfOperations[len(stackOfOperations) - 1]["command"] == "add":
            listOfTransactions.pop()
            stackOfOperations.pop()
        elif stackOfOperations[len(stackOfOperations) - 1]["command"] == "removeEven":
            while len(stackOfOperations) > 0 and stackOfOperations[len(stackOfOperations) - 1]["command"] == "removeEven":
                listOfTransactions.append({"apartment": stackOfOperations[len(stackOfOperations) - 1]["apartment"], "type": stackOfOperations[len(stackOfOperations) - 1]["type"], "amount": stackOfOperations[len(stackOfOperations) - 1]["amount"]})
                stackOfOperations.pop()
        elif stackOfOperations[len(stackOfOperations) - 1]["command"] == "removeOdd":
            while len(stackOfOperations) > 0 and stackOfOperations[len(stackOfOperations) - 1]["command"] == "removeOdd":
                listOfTransactions.append({"apartment": stackOfOperations[len(stackOfOperations) - 1]["apartment"], "type": stackOfOperations[len(stackOfOperations) - 1]["type"], "amount": stackOfOperations[len(stackOfOperations) - 1]["amount"]})
                stackOfOperations.pop()
        elif stackOfOperations[len(stackOfOperations) - 1]["command"] == "replace":
            for i in listOfTransactions:
                if stackOfOperations[len(stackOfOperations) - 1]["command"] == "replace":
                    if i["apartment"] == stackOfOperations[len(stackOfOperations) - 1]["apartment"]:
                        i["amount"] = stackOfOperations[len(stackOfOperations) - 1]["amount"]
                        stackOfOperations.pop()
                        break
        elif stackOfOperations[len(stackOfOperations) - 1]["command"] == "filterEven":
            while len(stackOfOperations) > 0 and stackOfOperations[len(stackOfOperations) - 1]["command"] == "filterEven":
                listOfTransactions.append({"apartment": stackOfOperations[len(stackOfOperations) - 1]["apartment"], "type": stackOfOperations[len(stackOfOperations) - 1]["type"], "amount": stackOfOperations[len(stackOfOperations) - 1]["amount"]})
                stackOfOperations.pop()
        elif stackOfOperations[len(stackOfOperations) - 1]["command"] == "filterOdd":
            while len(stackOfOperations) > 0 and stackOfOperations[len(stackOfOperations) - 1]["command"] == "filterOdd":
                listOfTransactions.append({"apartment": stackOfOperations[len(stackOfOperations) - 1]["apartment"], "type": stackOfOperations[len(stackOfOperations) - 1]["type"], "amount": stackOfOperations[len(stackOfOperations) - 1]["amount"]})
                stackOfOperations.pop()
    else:
        raise Exception("No more undos available")


def getApartmentForPrint(userInput):
    apartment = ""
    i = 5
    while i < len(userInput) and userInput[i] != " ":
        apartment += userInput[i]
        i += 1
        if i == len(userInput):
            break
    return apartment

def getExpenseForPrint(userInput):
    expense = ""
    i = 7
    while i < len(userInput) and userInput[i] != " ":
        expense += userInput[i]
        i += 1
        if i == len(userInput):
            break
    return expense

def getValueFromTransactionFilter(userInput):
    valueForFilter = ""
    i = 7
    while i < len(userInput) and userInput[i] != " ":
        valueForFilter += userInput[i]
        i += 1
        if i == len(userInput):
            break
    return valueForFilter

def manageFilterTransaction(userInput, listOfTransactions, stackOfOperations):
    valueForFilter = getValueFromTransactionFilter(userInput)

    if valueForFilter == "":
        raise Exception("Not enough parameters for filter command")

    filterType = "filterEven"

    if stackOfOperations and stackOfOperations[-1]["command"] == "filterEven":
        filterType = "filterOdd"

    if valueForFilter.isnumeric():
        i = len(listOfTransactions) - 1

        while i >= 0:
            transaction = listOfTransactions[i]

            if int(transaction["amount"]) > int(valueForFilter):
                stackOfOperations.append({"command": filterType, "apartment": transaction["apartment"], "amount": transaction["amount"], "type": transaction["type"]})
                listOfTransactions.pop(i)

            i -= 1
    else:
        i = len(listOfTransactions) - 1

        while i >= 0:
            transaction = listOfTransactions[i]

            if transaction["type"] != valueForFilter:
                stackOfOperations.append({"command": filterType, "apartment": transaction["apartment"], "amount": transaction["amount"], "type": transaction["type"]})
                listOfTransactions.pop(i)

            i -= 1


def filterUserInput(userInput, listOfTransactions, stackOfOperations):
    command = ""
    i = 0
    while i < len(userInput) and userInput[i] != " ":
        command += userInput[i]
        i += 1
    if command == "add":
        manageNewTransaction(userInput, listOfTransactions, stackOfOperations)
    elif command == "remove":
        manageRemoveTransaction(userInput, listOfTransactions, stackOfOperations)
    elif command == "list":
        manageListTransactions(userInput, listOfTransactions)
    elif command == "undo":
        manageUndo(listOfTransactions, stackOfOperations)
    elif command == "replace":
        manageReplaceTransaction(userInput, listOfTransactions, stackOfOperations)
    elif command == "filter":
        manageFilterTransaction(userInput, listOfTransactions, stackOfOperations)
    elif command == "exit":
        raise Exception("Program terminated")
    else:
        raise Exception("Invalid command")


