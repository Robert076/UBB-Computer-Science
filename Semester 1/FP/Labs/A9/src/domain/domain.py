import unittest

# Movie

class Movie:
    def __init__(self, movieId, movieTitle, movieDescription, movieGenre):
        self.movieId = movieId
        self.title = movieTitle
        self.description = movieDescription
        self.genre = movieGenre

    def getId(self):
        return self.movieId

    def getTitle(self):
        return self.title

    def getDescription(self):
        return self.description

    def getGenre(self):
        return self.genre

class TestMovieClass(unittest.TestCase):

    def setUp(self):
        self.movie1 = Movie(1, "Movie 1", "Description 1", "Genre 1")
        self.movie2 = Movie(2, "Movie 2", "Description 2", "Genre 2")

    def test_getId(self):
        self.assertEqual(self.movie1.getId(), 1)
        self.assertEqual(self.movie2.getId(), 2)

    def test_getTitle(self):
        self.assertEqual(self.movie1.getTitle(), "Movie 1")
        self.assertEqual(self.movie2.getTitle(), "Movie 2")

    def test_getDescription(self):
        self.assertEqual(self.movie1.getDescription(), "Description 1")
        self.assertEqual(self.movie2.getDescription(), "Description 2")

    def test_getGenre(self):
        self.assertEqual(self.movie1.getGenre(), "Genre 1")
        self.assertEqual(self.movie2.getGenre(), "Genre 2")


# Client



class Client:
    def __init__(self, clientId, clientName):
        self.clientId = clientId
        self.name = clientName

    def getId(self):
        return self.clientId

    def getName(self):
        return self.name

class TestClientClass(unittest.TestCase):
    def setUp(self):
        self.client1 = Client(1, "Robert")
        self.client2 = Client(2, "Caleb")

    def test_getId(self):
        self.assertEqual(self.client1.getId(), 1)
        self.assertEqual(self.client2.getId(), 2)

    def test_getName(self):
        self.assertEqual(self.client1.getName(), "Robert")
        self.assertEqual(self.client2.getName(), "Caleb")



# Rental



class Rental:
    def __init__(self, rentalId, movieId, clientId, rentedDate, dueDate, returnedDate):
        self.rentalId = rentalId
        self.movieId = movieId
        self.clientId = clientId
        self.rentedDate = rentedDate
        self.dueDate = dueDate
        self.returnedDate = returnedDate

    def getRentalId(self):
        return self.rentalId

    def getMovieId(self):
        return self.movieId

    def getClientId(self):
        return self.clientId

    def getRentedDate(self):
        return self.rentedDate

    def getDueDate(self):
        return self.dueDate

    def getReturnedDate(self):
        return self.returnedDate



class TestRentalClass(unittest.TestCase):

    def setUp(self):
        # Create instances of the Rental class for testing
        self.rental1 = Rental(1, 101, 201, "2023-01-01", "2023-01-10", "2023-01-05")
        self.rental2 = Rental(2, 102, 202, "2023-02-01", "2023-02-10", "2023-02-05")

    def test_getRentalId(self):
        self.assertEqual(self.rental1.getRentalId(), 1)
        self.assertEqual(self.rental2.getRentalId(), 2)

    def test_getMovieId(self):
        self.assertEqual(self.rental1.getMovieId(), 101)
        self.assertEqual(self.rental2.getMovieId(), 102)

    def test_getClientId(self):
        self.assertEqual(self.rental1.getClientId(), 201)
        self.assertEqual(self.rental2.getClientId(), 202)

    def test_getRentedDate(self):
        self.assertEqual(self.rental1.getRentedDate(), "2023-01-01")
        self.assertEqual(self.rental2.getRentedDate(), "2023-02-01")

    def test_getDueDate(self):
        self.assertEqual(self.rental1.getDueDate(), "2023-01-10")
        self.assertEqual(self.rental2.getDueDate(), "2023-02-10")

    def test_getReturnedDate(self):
        self.assertEqual(self.rental1.getReturnedDate(), "2023-01-05")
        self.assertEqual(self.rental2.getReturnedDate(), "2023-02-05")



if __name__ == '__main__':
    unittest.main()