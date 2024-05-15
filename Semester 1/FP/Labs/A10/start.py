from board.board import *
from services.services import *
from ui.ui import *

def startApp():
    try:
        board = Board()
        services = Services(board)
        ui = UI(services)
        ui.printRules()
        ui.printBoard()
        while True:
            ui.runGame()
    except Exception as exception:
        print(exception)

startApp()