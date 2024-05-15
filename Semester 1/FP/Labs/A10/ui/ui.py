class UI:
    def __init__(self, services):
        """
        Constructor that connects the ui to its corresponding services
        :param services: the services it connects to
        """
        self.services = services

    @staticmethod
    def printRules():
        """
        Static method that prints the rules of the game before the game starts, so the user knows how to play it
        :return: does not return anything
        """
        print("Rules: ")
        print("1. For a move to be considered valid, the input must be : X Y, separated by just one space. Otherwise it will not count as valid. Rows are 0-5, columns are 0-6")
        print("Example of a valid input: 4 5")
        print("2. For a move to be considered valid, it must be on the first non-empty row starting from the bottom")
        print("For example, if the bottom row is full, and the second to bottom row is not full, that one must be filled before advancing to the third row from the bottom")
        print("3. Third party engines are not allowed, nor is searching up the best move on the internet allowed")
        print("4. If you wish to exit the game, enter the input XXX")
        print("5. Have fun!")
        print("")
        print("")

    def printBoard(self):
        """
        Method that prints the board in a nice way that is easy for the user to read and understand
        :return: does not return anything
        """

        boardRows = 6
        boardCols = 7

        print("-----------------------------------------")
        for row in range(0, boardRows):
            for col in range(0, boardCols):
                element = self.services.board.getPosition(int(row), int(col))
                print(f"| {element} | ", end = "")
            print("")

        print("-----------------------------------------")

    def runGame(self):
        """
        Method that starts the game
        :return: does not return anything
        """
        userMove = input("Please enter a move: ")
        self.manageUserMove(userMove)
        self.printBoard()
        self.services.manageComputerMove()
        self.printBoard()
        self.services.checkIfGameIsOver()

    def manageUserMove(self, userMove):
        rowPositionInValidInputString = 0
        colPositionInValidInputString = 2

        if len(userMove) < 2:
            raise InputError("Invalid input!")

        row = userMove[rowPositionInValidInputString]
        col = userMove[colPositionInValidInputString]

        self.services.validateUserMove(row, col)

class InputError(Exception):
    pass
