from src.domain.domain import *
from src.repository.repository import *
from src.services.services import *
from src.ui.ui import *

def startApp():
    try:
        repository = MemoryRepository(Student)
        services = Services(repository)
        menu = UI(services)
        menu.runMenu()
    except Exception as exception:
        print(exception)


startApp()