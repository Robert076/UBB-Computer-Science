from src.domain.domain import *
from src.repository.repository import *
from src.services.services import *
from src.ui.ui import *
import configparser
def startApp():
    try:
        stackOfOperations = []
        redoStack = []
        config = configparser.ConfigParser()
        config.read_string("[DEFAULT]\n" + open('settings.properties').read())
        repository = config.get('DEFAULT', 'repository', fallback='inmemory')
        if repository == "inmemory":
            movieRepository = MemoryMovieRepository(Movie)
            movieServices = MovieServices(movieRepository)
            clientRepository = MemoryClientRepository(Client)
            clientServices = ClientServices(clientRepository)
            rentalRepository = MemoryRentalRepository(Rental)
            rentalServices = RentalServices(rentalRepository)
            menu = UI(movieServices, clientServices, rentalServices)
        elif repository == "binaryfile":
            movieRepository = BinaryFileMovieRepository(Movie)
            movieServices = MovieServices(movieRepository)
            clientRepository = BinaryFileClientRepository(Client)
            clientServices = ClientServices(clientRepository)
            rentalRepository = BinaryFileRentalRepository(Rental)
            rentalServices = RentalServices(rentalRepository)
            menu = UI(movieServices, clientServices, rentalServices)
        elif repository == "textfile":
            movieRepository = TextFileMovieRepository(Movie)
            movieServices = MovieServices(movieRepository)
            clientRepository = TextFileClientRepository(Client)
            clientServices = ClientServices(clientRepository)
            rentalRepository = TextFileRentalRepository(Rental)
            rentalServices = RentalServices(rentalRepository)
            menu = UI(movieServices, clientServices, rentalServices)
        menu.runMenu(stackOfOperations, redoStack)
    except Exception as exception:
        print(exception)


startApp()