class Board:
    """
    Our board has 3 possible values on one of its squares
    0 - the square is empty (i.e. it is possible to choose it)
    X - the square is taken by the player
    Y - the square is taken by the computer
    """
    def __init__(self):
        self.board = [
            [0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0]
        ]
    def getPosition(self, x, y):
        """
        Method that returns the value on a given position on the board
        :param x: x coordinate
        :param y: y coordinate
        :return: the value on the chosen square
        """
        return self.board[x][y]

    def setPosition(self, x, y, value):
        """
        Method that sets a new value on the board
        :param x:
        :param y:
        :param value:
        :return:
        """
        x = int(x)
        y = int(y)

        self.board[x][y] = value

