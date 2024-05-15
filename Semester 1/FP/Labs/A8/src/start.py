from src.domain.domain import *
from src.repository.repository import *
from src.services.services import *
from src.ui.ui import *
def startApp():
    try:
        movieRepository = MovieRepository(Movie)
        movieServices = MovieServices(movieRepository)
        clientRepository = ClientRepository(Client)
        clientServices = ClientServices(clientRepository)
        rentalRepository = RentalRepository(Rental)
        rentalServices = RentalServices(rentalRepository)
        menu = UI(movieServices, clientServices, rentalServices)
        menu.runMenu()
    except Exception as exception:
        print(exception)


startApp()