#
# This module is used to invoke the program's UI and start it. It should not contain a lot of code.
#

from ui import *
from functions import *

def startApp():
    listOfTransactions = [{"apartment": "1", "type": "gas", "amount": 100}, {"apartment": "2", "type": "water", "amount": 200}, {"apartment": "1", "type": "other", "amount": 300}, {"apartment": "1", "type": "heating", "amount": 400}, {"apartment": "1", "type": "electricity", "amount": 500}]
    stackOfOperations = []
    try:
        while True:
            userInput = getUserInput()
            filterUserInput(userInput, listOfTransactions, stackOfOperations)
    except Exception as exception:
        print(exception)

startApp()