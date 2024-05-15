import random

class Services:
    def __init__(self, board):
        """
        Constructor that connects the services to the board
        :param board: the board we connect services to
        """
        self.board = board

    def validateUserMove(self, row, col):
        """
        Method that validates a move made by the player, and if it is incorrect it terminates the game and raises and exception
        :param row: the row that the user entered
        :param col: the column that the user entered
        :return: does not return anything
        """
        if row == 'X' and col == 'X':
            exit("Thank you for playing")

        if row.isnumeric() == False or col.isnumeric() == False:
            raise ServicesError("Input for new move must be numeric!")

        if int(row) < 0 or int(row) > 5 or int(col) < 0 or int(col) > 6:
            raise ServicesError("Input for new move must be within the size of the board!")

        if self.board.getPosition(int(row), int(col)) != 0:
            raise ServicesError("Input for new move illegal. Position is already taken!")

        checkRow = int(row) + 1 # here we check whether the move is on the first nonempty row starting from the bottom
        boardSizeRows = 6
        boardSizeCols = 7
        while checkRow < boardSizeRows:
            checkCol = 0
            while checkCol < boardSizeCols:
                if self.board.getPosition(checkRow, checkCol) == 0:
                    raise ServicesError("Your move must be on the closest nonempty row to the bottom!")
                checkCol += 1
            checkRow += 1

        self.makeUserMove(row, col)


    def makeUserMove(self, row, col):
        """
        Method that places the user's move on the board after it has been validated
        :param row: the row of the move
        :param col: the column of the move
        :return: does not return anything
        """
        userMove = 'X'

        self.board.setPosition(row, col, userMove)

    def doWeYoloItOrDoWeTryhard(self):
        """
        Method that makes it 1 in 6 that the computer yolos the move and runs it down or 5 in 6 that it actually uses some logic
        :return:
        """
        # 1 is yolo 2, 3, 4, 5, 6 is tryhard
        judgement = random.randint(1, 6)
        return judgement

    def manageComputerMove(self):
        """
        Method that contains other methods, one responsible for judging what move the computer should make and one responsible on, and placing the move on the board
        :return: does not return anything
        """
        judgement = self.doWeYoloItOrDoWeTryhard()
        if judgement == 1:
            row, col = self.yoloMove()
        else:
            row, col = self.tryhardMove()
        self.board.setPosition(row, col, 'Y')

    def yoloMove(self):
        """
        Method that yolos the move on the first available square without any thinking behind it
        :return: returns a bad move for the computer
        """
        rowOnWhichWeMakeTheMove = 5

        boardSizeCols = 7

        while rowOnWhichWeMakeTheMove >= 0:
            colOnWhichWeMakeTheMove = 0
            while colOnWhichWeMakeTheMove < boardSizeCols:
                if self.board.getPosition(rowOnWhichWeMakeTheMove, colOnWhichWeMakeTheMove) == 0:
                    return rowOnWhichWeMakeTheMove, colOnWhichWeMakeTheMove
                colOnWhichWeMakeTheMove += 1
            rowOnWhichWeMakeTheMove -= 1


    def tryhardMove(self):
        """
        Method that makes a tryhard move(blocks a one move win and makes a one move win when it can)
        Be aware that if there is a way for X to win on both sides, it will only block one of them
        :return: returns a good move for the computer
        """
        boardSizeRows = 6
        boardSizeCols = 6

        currentRow = 0

        while currentRow < boardSizeRows:
            sumX = 0
            sumY = 0
            currentCol = 0
            while currentCol < boardSizeCols:
                if self.board.getPosition(currentRow, currentCol) == 'X':
                    sumX += 1
                    sumY = 0
                elif self.board.getPosition(currentRow, currentCol) == 'Y':
                    sumY += 1
                    sumX = 0
                elif self.board.getPosition(currentRow, currentCol) == 0:
                    sumX = 0
                    sumY = 0
                if sumY == 3 and self.board.getPosition(currentRow, currentCol + 1) == 0:
                    return currentRow, currentCol + 1
                elif sumX == 3 and self.board.getPosition(currentRow, currentCol + 1) == 0:
                    return currentRow, currentCol + 1

                currentCol += 1
            currentRow += 1

        currentCol = 0
        boardSizeCols = 7

        while currentCol < boardSizeCols:
            sumX = 0
            sumY = 0
            currentRow = 5
            while currentRow > 0:
                if self.board.getPosition(currentRow, currentCol) == 'X':
                    sumX += 1
                    sumY = 0
                elif self.board.getPosition(currentRow, currentCol) == 'Y':
                    sumY += 1
                    sumX = 0
                elif self.board.getPosition(currentRow, currentCol) == 0:
                    sumX = 0
                    sumY = 0
                if sumY == 3 and self.board.getPosition(currentRow, currentCol - 1) == 0:
                    return currentRow, currentCol - 1
                elif sumX == 3 and self.board.getPosition(currentRow, currentCol - 1) == 0:
                    return currentRow, currentCol - 1
                currentRow -= 1
            currentCol += 1

        currentRow = 5
        boardSizeCols = 6

        while currentRow >= 0:
            currentCol = 0
            while currentCol < boardSizeCols:
                if self.board.getPosition(currentRow, currentCol) == 'X':
                    if self.board.getPosition(currentRow, currentCol + 1) == 0:
                        return currentRow, currentCol + 1
                    if currentCol > 0:
                        if self.board.getPosition(currentRow, currentCol - 1) == 0:
                            return currentRow, currentCol - 1
                currentCol += 1
            currentRow -= 1

        return self.yoloMove()

    def checkIfGameIsOver(self):
        """
        Method that checks whether the game is over by counting the number of consecutive similar elements on the same row or column
        :return: does not return anything
        """

        gameIsDrawn = True

        boardSizeRows = 6
        boardSizeCols = 7

        currentRow = 0

        while currentRow < boardSizeRows:
            sumX = 0
            sumY = 0
            currentCol = 0
            while currentCol < boardSizeCols:
                if self.board.getPosition(currentRow, currentCol) == 'X':
                    sumX += 1
                    sumY = 0
                elif self.board.getPosition(currentRow, currentCol) == 'Y':
                    sumY += 1
                    sumX = 0
                elif self.board.getPosition(currentRow, currentCol) == 0:
                    gameIsDrawn = False
                    sumX = 0
                    sumY = 0
                if sumX == 4:
                    exit("You won!")
                elif sumY == 4:
                    exit("Computer won!")

                currentCol += 1
            currentRow += 1

        currentCol = 0
        while currentCol < boardSizeCols:
            sumX = 0
            sumY = 0
            currentRow = 5
            while currentRow >= 0:
                if self.board.getPosition(currentRow, currentCol) == 'X':
                    sumX += 1
                    sumY = 0
                elif self.board.getPosition(currentRow, currentCol) == 'Y':
                    sumY += 1
                    sumX = 0
                elif self.board.getPosition(currentRow, currentCol) == 0:
                    sumX = 0
                    sumY = 0
                if sumX == 4:
                    exit("You won!")
                elif sumY == 4:
                    exit("Computer won!")
                currentRow -= 1
            currentCol += 1
        if gameIsDrawn:
            exit("Draw!")



class ServicesError(Exception):
    pass