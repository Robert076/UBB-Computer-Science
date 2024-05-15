import unittest
import os
import pickle
from src.domain.domain import Movie, Client, Rental

import configparser

config = configparser.ConfigParser()
config.read_string("[DEFAULT]\n" + open('settings.properties').read())

movies = config.get('DEFAULT', 'movies', fallback='')
clients = config.get('DEFAULT', 'clients', fallback='')
rentals = config.get('DEFAULT', 'rentals', fallback='')

class MovieRepository:
    def __init__(self, domain):
        self.movieList = []
        self.domain = domain

        self.movieList.append(domain(1, "The Shawshank Redemption",
                                    "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                                    "Drama"))
        self.movieList.append(domain(2, "The Godfather",
                                    "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                                    "Crime"))
        self.movieList.append(domain(3, "The Dark Knight",
                                    "When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.",
                                    "Action"))
        self.movieList.append(domain(4, "Pulp Fiction",
                                    "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                                    "Crime"))
        self.movieList.append(domain(5, "Schindler's List",
                                    "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce.",
                                    "Biography"))
        self.movieList.append(domain(6, "The Lord of the Rings: The Return of the King",
                                    "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom.",
                                    "Adventure"))
        self.movieList.append(domain(7, "Forrest Gump",
                                    "The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other history unfold through the perspective of an Alabama man with an IQ of 75.",
                                    "Drama"))
        self.movieList.append(domain(8, "Fight Club",
                                    "An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into much more.",
                                    "Drama"))
        self.movieList.append(domain(9, "Inception",
                                    "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                                    "Action"))
        self.movieList.append(domain(10, "The Matrix",
                                    "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                                    "Action"))
        self.movieList.append(domain(11, "The Silence of the Lambs",
                                    "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer.",
                                    "Crime"))
        self.movieList.append(domain(12, "The Departed",
                                    "An undercover cop and a mole in the police attempt to identify each other while infiltrating an Irish gang in South Boston.",
                                    "Crime"))
        self.movieList.append(domain(13, "The Shawshank Redemption",
                                    "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                                    "Drama"))
        self.movieList.append(domain(14, "Gladiator",
                                    "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.",
                                    "Action"))
        self.movieList.append(domain(15, "The Green Mile",
                                    "The lives of guards on Death Row are affected by one of their charges: a black man accused of child murder and rape, yet who has a mysterious gift.",
                                    "Crime"))
        self.movieList.append(domain(16, "The Godfather: Part II",
                                    "The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.",
                                    "Crime"))
        self.movieList.append(domain(17, "The Lord of the Rings: The Fellowship of the Ring",
                                    "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.",
                                    "Adventure"))
        self.movieList.append(domain(18, "Goodfellas",
                                    "The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.",
                                    "Biography"))
        self.movieList.append(domain(19, "Schindler's List",
                                    "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce.",
                                    "Biography"))
        self.movieList.append(domain(20, "The Usual Suspects",
                                    "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which began when five criminals met at a seemingly random police lineup.",
                                    "Crime"))

class MemoryMovieRepository(MovieRepository):
    def getMovieList(self):
        return self.movieList

    def setMovieList(self, newMovieList):
        self.movieList = newMovieList

class TextFileMovieRepository(MovieRepository):
    textFile = movies

    def __init__(self, domain):
        super().__init__(domain)
        if not os.path.exists(self.textFile):
            self.setMovieList(self.movieList)
        self.getMovieList()

    def setMovieList(self, movieList):
        with open(self.textFile, "w") as textRepo:
            for movie in movieList:
                textRepo.write(str(movie.getId()) + "," + str(movie.getTitle()) + "," + str(movie.getDescription()) + "," + movie.getGenre() + "\n")

    def getMovieList(self):
        movieList = []
        with open(self.textFile, "r") as textRepo:
            for line in textRepo.readlines():
                line = line.strip()
                movieId, title, desc, genre = line.split(maxsplit=3, sep=",")
                movie = self.domain(str(movieId), str(title), str(desc), str(genre))
                movieList.append(movie)
        return movieList

class BinaryFileMovieRepository(MovieRepository):
    binaryFile = movies

    def __init__(self, domain):
        super().__init__(domain)
        if not os.path.exists(self.binaryFile):
            self.setMovieList(self.movieList)
        self.getMovieList()

    def setMovieList(self, list):
        with open(self.binaryFile, "wb") as binaryRepo:
            pickle.dump(list, binaryRepo)

    def getMovieList(self):
        with open(self.binaryFile, "rb") as binaryRepo:
            movieList = pickle.load(binaryRepo)
        return movieList
class TestMemoryMovieRepository(unittest.TestCase):
    def setUp(self):
        self.repository = MemoryMovieRepository(Movie)

    def test_getMovieList(self):
        movieList = self.repository.getMovieList()
        self.assertIsInstance(movieList, list)

        self.assertGreater(len(movieList), 0)
        for movie in movieList:
            self.assertIsInstance(movie, self.repository.domain)

    def test_setMovieList(self):
        newList = [self.repository.domain(21, "There's something in the barn", "Angry elves", "Horror"),
                   self.repository.domain(22, "Lord of the rings", "Idk", "Action")]
        self.repository.setMovieList(newList)
        updatedList = self.repository.getMovieList()
        self.assertEqual(newList, updatedList)

class ClientRepository:
    def __init__(self, domain):
        self.clientList = []
        self.domain = domain

        self.clientList.append(domain(1, "Robert"))
        self.clientList.append(domain(2, "Andrei"))
        self.clientList.append(domain(3, "Maria"))
        self.clientList.append(domain(4, "Ion"))
        self.clientList.append(domain(5, "Elena"))
        self.clientList.append(domain(6, "Alexandra"))
        self.clientList.append(domain(7, "Cristian"))
        self.clientList.append(domain(8, "Diana"))
        self.clientList.append(domain(9, "Mihai"))
        self.clientList.append(domain(10, "Ana"))
        self.clientList.append(domain(11, "Gabriel"))
        self.clientList.append(domain(12, "Laura"))
        self.clientList.append(domain(13, "Florin"))
        self.clientList.append(domain(14, "Adriana"))
        self.clientList.append(domain(15, "Razvan"))
        self.clientList.append(domain(16, "Carmen"))
        self.clientList.append(domain(17, "George"))
        self.clientList.append(domain(18, "Eva"))
        self.clientList.append(domain(19, "Radu"))
        self.clientList.append(domain(20, "Simona"))

    def getClientList(self):
        return self.clientList

    def setClientList(self, newClientList):
        self.clientList = newClientList

class MemoryClientRepository(ClientRepository):
    def getClientList(self):
        return self.clientList

    def setClientList(self, newClientList):
        self.clientList = newClientList

class TextFileClientRepository(ClientRepository):
    textFile = "ClientTextFileRepository.txt"

    def __init__(self, domain):
        super().__init__(domain)
        if not os.path.exists(self.textFile):
            self.setClientList(self.clientList)
        self.getClientList()

    def setClientList(self, clientList):
        with open("ClientTextFileRepository.txt", "w") as textRepo:
            for client in clientList:
                textRepo.write(str(client.getId()) + "," + str(client.getName()) + "\n")

    def getClientList(self):
        clientList = []
        with open("ClientTextFileRepository.txt", "r") as textRepo:
            for line in textRepo.readlines():
                line = line.strip()
                clientId, name = line.split(maxsplit=1, sep=",")
                client = self.domain(str(clientId), str(name))
                clientList.append(client)
        return clientList

class BinaryFileClientRepository(ClientRepository):
    binaryFile = "ClientBinaryFileRepository.txt"

    def __init__(self, domain):
        super().__init__(domain)
        if not os.path.exists(self.binaryFile):
            self.setClientList(self.clientList)
        self.getClientList()

    def setClientList(self, list):
        with open("ClientBinaryFileRepository.pickle", "wb") as binaryRepo:
            pickle.dump(list, binaryRepo)

    def getClientList(self):
        with open("ClientBinaryFileRepository.pickle", "rb") as binaryRepo:
            clientList = pickle.load(binaryRepo)
        return clientList

class TestMemoryClientRepository(unittest.TestCase):
    def setUp(self):
        self.repository = MemoryClientRepository(Client)

    def test_getClientList(self):
        clientList = self.repository.getClientList()
        self.assertIsInstance(clientList, list)
        self.assertGreater(len(clientList), 0)
        for client in clientList:
            self.assertIsInstance(client, self.repository.domain)

    def test_setClientList(self):
        newList = [self.repository.domain(21, "Marco"),
                   self.repository.domain(22, "Andreas")]
        self.repository.setClientList(newList)
        updatedList = self.repository.getClientList()
        self.assertEqual(newList, updatedList)

class RentalRepository:
    def __init__(self, domain):
        self.rentalList = []
        self.domain = domain

        self.rentalList.append(domain(1, 1, 2, "01 01 2023", "10 01 2023", "05 01 2023"))
        self.rentalList.append(domain(2, 1, 2, "01 02 2023", "10 02 2023", "05 02 2023"))
        self.rentalList.append(domain(3, 1, 2, "01 03 2023", "10 03 2023", "05 03 2023"))
        self.rentalList.append(domain(4, 5, 2, "01 04 2023", "10 04 2023", "05 04 2023"))
        self.rentalList.append(domain(5, 5, 2, "01 05 2023", "10 05 2023", "05 05 2023"))
        self.rentalList.append(domain(6, 5, 3, "01 06 2023", "10 06 2023", "05 06 2023"))
        self.rentalList.append(domain(7, 5, 5, "01 07 2023", "10 07 2023", "05 07 2023"))
        self.rentalList.append(domain(8, 5, 6, "01 08 2023", "10 08 2023", "05 08 2023"))
        self.rentalList.append(domain(9, 10, 8, "01 09 2023", "10 09 2023", "05 09 2023"))
        self.rentalList.append(domain(10, 10, 8, "01 10 2023", "10 10 2023", "05 10 2023"))
        self.rentalList.append(domain(11, 11, 8, "01 11 2023", "10 11 2023", "05 11 2023"))
        self.rentalList.append(domain(12, 12, 20, "01 12 2023", "10 12 2023", "05 12 2023"))
        self.rentalList.append(domain(13, 13, 20, "01 01 2024", "10 01 2024", "05 01 2024"))
        self.rentalList.append(domain(14, 14, 20, "01 02 2024", "10 02 2024", "05 02 2024"))
        self.rentalList.append(domain(15, 15, 20, "01 03 2024", "10 03 2024", "05 03 2024"))
        self.rentalList.append(domain(16, 15, 20, "01 04 2024", "10 04 2024", "05 04 2024"))
        self.rentalList.append(domain(17, 17, 20, "01 05 2024", "10 05 2024", "05 05 2024"))
        self.rentalList.append(domain(18, 18, 20, "01 06 2024", "10 06 2024", "05 06 2024"))
        self.rentalList.append(domain(19, 19, 219, "01 07 2024", "10 07 2024", "10 08 2024"))
        self.rentalList.append(domain(20, 10, 220, "01 08 2024", "10 08 2024", "14 09 2024"))

    def getRentalList(self):
        return self.rentalList

    def setRentalList(self, newRentalList):
        self.rentalList = newRentalList

class MemoryRentalRepository(RentalRepository):
    def getRentalList(self):
        return self.rentalList

    def setRentalList(self, newRentalList):
        self.rentalList = newRentalList

class TestMemoryRentalRepository(unittest.TestCase):
    def setUp(self):
        self.repository = MemoryRentalRepository(Rental)

    def test_getRentalList(self):
        rentalList = self.repository.getRentalList()
        self.assertIsInstance(rentalList, list)
        self.assertGreater(len(rentalList), 0)
        for rental in rentalList:
            self.assertIsInstance(rental, self.repository.domain)

    def test_setRentalList(self):
        newList = [self.repository.domain(20, 121, 221, "01 02 2024", "10 03 2024", "05 05 2024"),
                   self.repository.domain(20, 122, 222, "01 01 2024", "10 02 2024", "05 04 2024")]
        self.repository.setRentalList(newList)
        updatedList = self.repository.getRentalList()
        self.assertEqual(newList, updatedList)

class TextFileRentalRepository(RentalRepository):
    textFile = "RentalTextFileRepository.txt"

    def __init__(self, domain):
        super().__init__(domain)
        if not os.path.exists(self.textFile):
            self.setRentalList(self.rentalList)
        self.getRentalList()

    def setRentalList(self, rentalList):
        with open("RentalTextFileRepository.txt", "w") as textRepo:
            for rental in rentalList:
                textRepo.write(str(rental.getRentalId()) + "," + str(rental.getMovieId()) + "," + str(rental.getClientId()) + "," + rental.getRentedDate() + "," + rental.getDueDate() + "," + rental.getReturnedDate() + "\n")

    def getRentalList(self):
        rentalList = []
        with open("RentalTextFileRepository.txt", "r") as textRepo:
            for line in textRepo.readlines():
                line = line.strip()
                rentalId, clientId, movieId, rentalDate, dueDate, returnDate = line.split(maxsplit=5, sep=",")
                rental = self.domain(str(rentalId), str(movieId), str(clientId), str(rentalDate), str(dueDate), str(returnDate))
                rentalList.append(rental)
        return rentalList

class BinaryFileRentalRepository(RentalRepository):
    binaryFile = "RentalBinaryFileRepository.txt"

    def __init__(self, domain):
        super().__init__(domain)
        if not os.path.exists(self.binaryFile):
            self.setRentalList(self.rentalList)
        self.getRentalList()

    def setRentalList(self, list):
        with open("RentalBinaryFileRepository.pickle", "wb") as binaryRepo:
            pickle.dump(list, binaryRepo)

    def getRentalList(self):
        with open("RentalBinaryFileRepository.pickle", "rb") as binaryRepo:
            rentalList = pickle.load(binaryRepo)
        return rentalList

class RepositoryError(Exception):
    pass

if __name__ == '__main__':
    unittest.main()