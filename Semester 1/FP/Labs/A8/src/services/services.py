import unittest
from src.repository.repository import *
from src.repository.repository import *
from src.services.services import MovieServices, ClientServices, RentalServices, ServicesError
class MovieServices:
    def __init__(self, repository):
        self.repository = repository

    def addMovie(self, movieId, movieTitle, movieDescription, movieGenre):
        movieList = self.repository.getMovieList()
        for movie in movieList:
            if int(movie.movieId) == int(movieId):
                raise ServicesError("Movie ID already exists!")
        newMovie = self.repository.domain(movieId, movieTitle, movieDescription, movieGenre)
        movieList.append(newMovie)
        self.repository.setMovieList(movieList)

    def removeMovie(self, removedMovieId):
        movieList = self.repository.getMovieList()
        i = 0
        didWeRemoveAMovie = False
        while i < len(movieList):
            if int(movieList[i].movieId) == int(removedMovieId):
                movieList.remove(movieList[i])
                didWeRemoveAMovie = True
                i -= 1
            i += 1
        if didWeRemoveAMovie == False:
            raise ServicesError("The movie you wanted to remove does not exist")
        self.repository.setMovieList(movieList)

    def updateMovie(self, updatedMovieId, updatedMovieTitle, updatedMovieDescription, updatedMovieGenre):
        movieList = self.repository.getMovieList()
        for movie in movieList:
            if movie.movieId == updatedMovieId:
                movie.title = updatedMovieTitle
                movie.description = updatedMovieDescription
                movie.genre = updatedMovieGenre
        self.repository.setMovieList(movieList)


class ClientServices:
    def __init__(self, repository):
        self.repository = repository

    def addClient(self, clientId, clientName):
        clientList = self.repository.getClientList()
        for client in clientList:
            if int(client.clientId) == int(clientId):
                raise ServicesError("Client ID already exists!")
        clientList.append(self.repository.domain(clientId, clientName))
        self.repository.setClientList(clientList)

    def removeClient(self, clientId):
        clientList = self.repository.getClientList()
        i = 0
        didWeRemoveAClient = False
        while i < len(clientList):
            if int(clientList[i].clientId) == int(clientId):
                clientList.remove(clientList[i])
                i -= 1
                didWeRemoveAClient = True
            i += 1
        if didWeRemoveAClient == False:
            raise ServicesError("The client you wanted to remove does not exist")
        self.repository.setClientList(clientList)

    def updateClient(self, updatedClientId, updatedClientName):
        clientList = self.repository.getClientList()
        for client in clientList:
            if int(client.clientId) == int(updatedClientId):
                client.name = updatedClientName
        self.repository.setClientList(clientList)
class RentalServices:
    def __init__(self, repository):
        self.repository = repository

    def getDueDates(self, dueDate):
        i = 0
        day = ""
        month = ""
        year = ""
        while i < len(dueDate) and dueDate[i] != ' ':
            day = day + dueDate[i]
            i += 1

        i += 1
        while i < len(dueDate) and dueDate[i] != ' ':
            month = month + dueDate[i]
            i += 1

        i += 1
        while i < len(dueDate):
            year = year + dueDate[i]
            i += 1

        if day == "" or month == "" or year == "":
            raise ServicesError("Invalid input for due date")

        return day, month, year

    def getReturnDates(self, returnedDate):
        i = 0
        day = ""
        month = ""
        year = ""

        while i < len(returnedDate) and returnedDate[i] != ' ':
            day = day + returnedDate[i]
            i += 1

        i += 1
        while i < len(returnedDate) and returnedDate[i] != ' ':
            month = month + returnedDate[i]
            i += 1

        i += 1
        while i < len(returnedDate):
            year = year + returnedDate[i]
            i += 1

        if day == "" or month == "" or year == "":
            raise ServicesError("Invalid input for returned date")

        return day, month, year

    def checkThatTheClientIsEligibleForRenting(self, clientId):
        rentalList = self.repository.getRentalList()
        for rental in rentalList:
            if int(rental.clientId) == int(clientId):
                dueDay, dueMonth, dueYear = self.getDueDates(rental.dueDate)
                returnDay, returnMonth, returnYear = self.getReturnDates(rental.returnedDate)
                dueDay = int(dueDay)
                dueMonth = int(dueMonth)
                dueYear = int(dueYear)
                returnDay = int(returnDay)
                returnMonth = int(returnMonth)
                returnYear = int(returnYear)

                if dueYear < returnYear or dueYear == returnYear and dueMonth < returnMonth or dueYear == returnYear and dueMonth == returnMonth and dueDay < returnDay:
                    raise ServicesError("This client is not eligible for rentals!")

    def addRental(self, rentalId, movieId, clientId, rentalDate, dueDate, returnedDate):
        self.checkThatTheClientIsEligibleForRenting(clientId)
        rentalList = self.repository.getRentalList()
        rentalList.append(self.repository.domain(rentalId, movieId, clientId, rentalDate, dueDate, returnedDate))
        self.repository.setRentalList(rentalList)


    def updateRental(self, rentalId, returnedDate):
        rentalList = self.repository.getRentalList()
        i = 0
        while i < len(rentalList):
            if int(rentalList[i].rentalId) == int(rentalId):
                rentalList[i].returnedDate = returnedDate
            i += 1
        self.repository.setRentalList(rentalList)

    def lateRentals(self):
        listOfLateRentals = []
        rentalList = self.repository.getRentalList()
        for rental in rentalList:
            dueDay, dueMonth, dueYear = self.getDueDates(rental.dueDate)
            returnDay, returnMonth, returnYear = self.getReturnDates(rental.returnedDate)
            dueDay = int(dueDay)
            dueMonth = int(dueMonth)
            dueYear = int(dueYear)
            returnDay = int(returnDay)
            returnMonth = int(returnMonth)
            returnYear = int(returnYear)

            if dueYear < returnYear or dueYear == returnYear and dueMonth < returnMonth or dueYear == returnYear and dueMonth == returnMonth and dueDay < returnDay:
                listOfLateRentals.append(rental)
        return listOfLateRentals

    def lateDaysOfRental(self, rental):
        dueDay, dueMonth, dueYear = self.getDueDates(rental.dueDate)
        returnDay, returnMonth, returnYear = self.getReturnDates(rental.returnedDate)
        dueDay = int(dueDay)
        dueMonth = int(dueMonth)
        dueYear = int(dueYear)
        returnDay = int(returnDay)
        returnMonth = int(returnMonth)
        returnYear = int(returnYear)
        count = 0
        while dueDay < returnDay:
            count += 1
            returnDay -= 1
        while dueMonth < returnMonth:
            count += 30
            returnMonth -= 1
        while dueYear < returnYear:
            count += 365
            returnYear -= 1

        return count


    def sortListOfLateRentalsByTheNumberOfDaysLate(self, rentalList):
        for i in range(0, len(rentalList) - 1):
            for j in range(i + 1, len(rentalList)):
                lateDaysForFirstRental = int(self.lateDaysOfRental(rentalList[i]))
                lateDaysForSecondRental = int(self.lateDaysOfRental(rentalList[j]))
                if lateDaysForFirstRental < lateDaysForSecondRental:
                    rentalList[i], rentalList[j] = rentalList[j], rentalList[i]

class ServicesError(Exception):
    pass

class TestMovieServices(unittest.TestCase):

    def setUp(self):
        self.repository = MovieRepository(Movie)
        self.movieServices = MovieServices(self.repository)

    def testAddMovie(self):
        self.repository.setMovieList([])
        self.movieServices.addMovie(1, "Title", "Description", "Genre")
        movieList = self.repository.getMovieList()
        self.assertEqual(len(movieList), 1)
        self.assertEqual(movieList[0].movieId, 1)
        with self.assertRaises(ServicesError):
            self.movieServices.addMovie(1, "Title", "Description", "Genre")

    def testRemoveMovie(self):
        self.repository.setMovieList([Movie(1, "Title", "Description", "Genre")])
        self.movieServices.removeMovie(1)
        movieList = self.repository.getMovieList()
        self.assertEqual(len(movieList), 0)
        with self.assertRaises(ServicesError):
            self.movieServices.removeMovie(1)

    def testUpdateMovie(self):
        self.repository.setMovieList([Movie(1, "Old Title", "Old Description", "Old Genre")])
        self.movieServices.updateMovie(1, "New Title", "New Description", "New Genre")
        movieList = self.repository.getMovieList()
        self.assertEqual(len(movieList), 1)
        self.assertEqual(movieList[0].title, "New Title")
        self.assertEqual(movieList[0].description, "New Description")
        self.assertEqual(movieList[0].genre, "New Genre")


class TestClientServices(unittest.TestCase):
    def setUp(self):
        self.repository = ClientRepository(Client)
        self.clientServices = ClientServices(self.repository)

    def testAddClient(self):
        self.repository.setClientList([])
        self.clientServices.addClient(1, "Name")
        clientList = self.repository.getClientList()
        self.assertEqual(len(clientList), 1)
        self.assertEqual(clientList[0].clientId, 1)
        with self.assertRaises(ServicesError):
            self.clientServices.addClient(1, "Name")

    def testRemoveClient(self):
        self.repository.setClientList([Client(1, "Name")])
        self.clientServices.removeClient(1)
        clientList = self.repository.getClientList()
        self.assertEqual(len(clientList), 0)
        with self.assertRaises(ServicesError):
            self.clientServices.removeClient(1)

    def testUpdateClient(self):
        self.repository.setClientList([Client(1, "Old Name")])
        self.clientServices.updateClient(1, "New Name")
        clientList = self.repository.getClientList()
        self.assertEqual(len(clientList), 1)
        self.assertEqual(clientList[0].name, "New Name")


class TestRentalServices(unittest.TestCase):

    def setUp(self):
        self.repository = RentalRepository(Rental)
        self.rentalServices = RentalServices(self.repository)

    def testAddRental(self):
        self.repository.setRentalList([])
        self.rentalServices.addRental(1, 101, 201, "01 01 2023", "10 01 2023", "05 01 2023")
        rentalList = self.repository.getRentalList()
        self.assertEqual(len(rentalList), 1)
        self.assertEqual(rentalList[0].rentalId, 1)
        self.repository.setRentalList([Rental(2, 102, 201, "01 02 2023", "10-02-2023", "05 02 2023")])
        with self.assertRaises(ServicesError):
            self.rentalServices.addRental(3, 103, 201, "01 03 2023", "10 03 2023", "05 03 2023")

    def testUpdateRental(self):
        self.repository.setRentalList([Rental(1, 101, 201, "01 01 2023", "10 01 2023", "05 01 2023")])
        self.rentalServices.updateRental(1, "10 01 2023")
        rentalList = self.repository.getRentalList()
        self.assertEqual(len(rentalList), 1)
        self.assertEqual(rentalList[0].returnedDate, "10 01 2023")

    def testLateRentals(self):
        self.repository.setRentalList([Rental(1, 101, 201, "01 01 2023", "10 01 2023", "15 01 2023")])
        lateRentals = self.rentalServices.lateRentals()
        self.assertEqual(len(lateRentals), 1)

    def testLateDaysOfRental(self):
        rental = Rental(1, 101, 201, "01 01 2023", "10 01 2023", "15 01 2023")
        lateDays = self.rentalServices.lateDaysOfRental(rental)
        self.assertEqual(lateDays, 5)

    def testSortListOfLateRentalsByTheNumberOfDaysLate(self):
        rental1 = Rental(1, 101, 201, "01 01 2023", "10 01 2023", "15 01 2023")
        rental2 = Rental(2, 102, 202, "05 01 2023", "12 01 2023", "18 01 2023")
        rentalList = [rental1, rental2]
        self.rentalServices.sortListOfLateRentalsByTheNumberOfDaysLate(rentalList)
        self.assertEqual(rentalList, [rental2, rental1])
if __name__ == '__main__':
    unittest.main()
