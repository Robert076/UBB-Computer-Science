class UI:
    def __init__(self, servicesMovie, servicesClient, servicesRental):
        self.servicesMovie = servicesMovie
        self.servicesClient = servicesClient
        self.servicesRental = servicesRental
    def runMenu(self, stackOfOperations, redoStack):
        while True:
            print("Please pick an option")
            print("1. Add a new movie")
            print("2. Remove a movie")
            print("3. Update a movie")
            print("4. List all movies")
            print("5. Add a new client")
            print("6. Remove a client")
            print("7. Update a client")
            print("8. List all clients")
            print("9. Add new rental")
            print("10. Return a rental")
            print("11. List all rentals")
            print("12. Search movie by id")
            print("13. Search movie by title")
            print("14. Search movie by description")
            print("15. Search movie by genre")
            print("16. Search client by id")
            print("17. Search client by name")
            print("18. List most rented movies")
            print("19. List most active clients")
            print("20. List late rentals")
            print("21. Undo")
            print("22. Redo")

            userInput = int(input("Enter your choice: "))

            addMovieOption = 1
            removeMovieOption = 2
            updateMovieOption = 3
            listMoviesOption = 4
            addClientOption = 5
            removeClientOption = 6
            updateClientOption = 7
            listClientsOption = 8
            rentMovieOption = 9
            returnMovieOption = 10
            listRentalsOption = 11
            searchMovieById = 12
            searchMovieByTitle = 13
            searchMovieByDescription = 14
            searchMovieByGenre = 15
            searchClientById = 16
            searchClientByName = 17
            mostRentedMovies = 18
            mostActiveClients = 19
            lateRentals = 20
            undoLastOperation = 21
            redoLastUndo = 22

            if userInput == addMovieOption:
                self.manageAddMovie(stackOfOperations)
            elif userInput == removeMovieOption:
                self.manageRemoveMovie(stackOfOperations)
            elif userInput == updateMovieOption:
                self.manageUpdateMovie(stackOfOperations)
            elif userInput == listMoviesOption:
                self.manageListMovies()
            elif userInput == addClientOption:
                self.manageAddClient(stackOfOperations)
            elif userInput == removeClientOption:
                self.manageRemoveClient(stackOfOperations)
            elif userInput == updateClientOption:
                self.manageUpdateClient(stackOfOperations)
            elif userInput == listClientsOption:
                self.manageListClients()
            elif userInput == rentMovieOption:
                self.manageRentMovie(stackOfOperations)
            elif userInput == returnMovieOption:
                self.manageReturnMovie(stackOfOperations)
            elif userInput == listRentalsOption:
                self.manageListRentals()
            elif userInput == searchMovieById:
                self.manageSearchMovieById()
            elif userInput == searchMovieByTitle:
                self.manageSearchMovieByTitle()
            elif userInput == searchMovieByDescription:
                self.manageSearchMovieByDescription()
            elif userInput == searchMovieByGenre:
                self.manageSearchMovieByGenre()
            elif userInput == searchClientById:
                self.manageSearchClientById()
            elif userInput == searchClientByName:
                self.manageSearchClientByName()
            elif userInput == mostRentedMovies:
                self.manageMostRentedMovies()
            elif userInput == mostActiveClients:
                self.manageMostActiveClients()
            elif userInput == lateRentals:
                self.manageLateRentals()
            elif userInput == undoLastOperation:
                self.manageUndo(stackOfOperations, redoStack)
            elif userInput == redoLastUndo:
                self.manageRedo(stackOfOperations, redoStack)

    def manageAddMovie(self, stackOfOperations):
        movieId = input("Enter the id of the new movie: ")
        if movieId == "":
            raise Exception("Invalid input for adding a new movie")
        movieTitle = input("Enter the title of the new movie: ")
        if movieTitle == "":
            raise Exception("Invalid input for adding a new movie")
        movieDescription = input("Enter the description of the new movie: ")
        if movieDescription == "":
            raise Exception("Invalid input for adding a new movie")
        movieGenre = input("Enter the genre of the new movie: ")
        if movieGenre == "":
            raise Exception("Invalid input for adding a new movie")
        self.servicesMovie.addMovie(movieId, movieTitle, movieDescription, movieGenre, stackOfOperations)

    def manageRemoveMovie(self, stackOfOperations):
        removedMovieId = int(input("Enter the id of the movie you want to delete: "))
        self.servicesMovie.removeMovie(removedMovieId, stackOfOperations)

    def manageUpdateMovie(self, stackOfOperations):
        updatedMovieId = int(input("Enter the id of the movie you want to update: "))
        updatedMovieTitle = input("Enter the updated name: ")
        updatedMovieDescription = input("Enter the updated description: ")
        updatedMovieGenre = input("Enter the updated genre: ")
        self.servicesMovie.updateMovie(updatedMovieId, updatedMovieTitle, updatedMovieDescription, updatedMovieGenre, stackOfOperations)

    def manageListMovies(self):
        movieList = self.servicesMovie.repository.getMovieList()
        for movie in movieList:
            print(f"{movie.movieId}. {movie.title} - {movie.description} - {movie.genre}")

    def manageAddClient(self, stackOfOperations):
        clientId = input("Enter the id of the new client: ")
        if clientId == "":
            raise Exception("Invalid new client id")
        clientName = input("Enter the name of the new client: ")
        if clientName == "":
            raise Exception("Invalid new client name")
        self.servicesClient.addClient(clientId, clientName, stackOfOperations)

    def manageRemoveClient(self, stackOfOperations):
        removedClientId = input("Enter the id of the client you want to remove: ")
        self.servicesClient.removeClient(removedClientId, stackOfOperations)

    def manageUpdateClient(self, stackOfOperations):
        updatedClientId = input("Enter the id of the client you want to update: ")
        updatedClientName = input("Enter the new name of the client you want to update: ")
        self.servicesClient.updateClient(updatedClientId, updatedClientName, stackOfOperations)

    def manageListClients(self):
        clientList = self.servicesClient.repository.getClientList()
        for client in clientList:
            print(f"{client.clientId}. {client.name}")

    def manageRentMovie(self, stackOfOperations):
        rentalId = input("Enter the id of the new rental: ")
        movieId = input("Enter the id of the movie in the new rental: ")
        clientId = input("Enter the id of the client in the new rental: ")
        rentedDate = input("Enter the date of the rental (DD MM YYYY): ")
        dueDate = input("Enter the due date (DD MM YYYY): ")
        returnedDate = input("Enter the date of the return of the movie (DD MM YYYY): ")
        self.servicesRental.addRental(rentalId, movieId, clientId, rentedDate, dueDate, returnedDate, stackOfOperations)

    def manageReturnMovie(self, stackOfOperations):
        returnId = input("Enter the id of the rental you want to return: ")
        returnDate = input("Enter the date of the return (DD MM YYYY): ")
        self.servicesRental.updateRental(returnId, returnDate, stackOfOperations)

    def manageListRentals(self):
        rentalList = self.servicesRental.repository.getRentalList()
        for rental in rentalList:
            print(f"{rental.rentalId}. {rental.clientId}. {rental.movieId}. {rental.rentedDate} / {rental.dueDate} / {rental.returnedDate}")

    def manageSearchMovieById(self):
        movieId = input("Enter the id you want to search for: ")
        movieList = self.servicesMovie.repository.getMovieList()
        for movie in movieList:
            if int(movie.movieId) == int(movieId):
                print(f"{movie.movieId}. {movie.title} - {movie.description} - {movie.genre}")

    def manageSearchMovieByTitle(self):
        movieTitle = input("Enter the title you want to search for: ")
        movieList = self.servicesMovie.repository.getMovieList()
        for movie in movieList:
            if movieTitle.lower() in movie.title.lower():
                print(f"{movie.movieId}. {movie.title} - {movie.description} - {movie.genre}")

    def manageSearchMovieByDescription(self):
        movieDescription = input("Enter the description you want to search for: ")
        movieList = self.servicesMovie.repository.getMovieList()
        for movie in movieList:
            if movieDescription.lower() in movie.description.lower():
                print(f"{movie.movieId}. {movie.title} - {movie.description} - {movie.genre}")

    def manageSearchMovieByGenre(self):
        movieGenre = input("Enter the genre you want to search for: ")
        movieList = self.servicesMovie.repository.getMovieList()
        for movie in movieList:
            if movieGenre.lower() in movie.genre.lower():
                print(f"{movie.movieId}. {movie.title} - {movie.description} - {movie.genre}")

    def manageSearchClientById(self):
        clientId = input("Enter the id you want to search for: ")
        clientList = self.servicesClient.repository.getClientList()
        for client in clientList:
            if int(client.clientId) == int(clientId):
                print(f"{client.clientId}. {client.name}")

    def manageSearchClientByName(self):
        name = input("Enter the name you want to search for: ")
        clientList = self.servicesClient.repository.getClientList()
        for client in clientList:
            if name.lower() in client.name:
                print(f"{client.clientId}. {client.name}")

    def manageMostRentedMovies(self):
        appearenceCounter = {}
        rentalList = self.servicesRental.repository.getRentalList()
        movieList = self.servicesMovie.repository.getMovieList()

        for rental in rentalList:
            id = int(rental.movieId)
            appearenceCounter[id] = appearenceCounter.get(id, 0) + 1

        movieList.sort(key=lambda movie: appearenceCounter.get(int(movie.movieId), 0), reverse=True)

        for movie in movieList:
            print(f"{movie.movieId}. {movie.title} - {movie.description} - {movie.genre}")

    def manageMostActiveClients(self):
        appearenceCounter = {}
        rentalList = self.servicesRental.repository.getRentalList()
        clientList = self.servicesClient.repository.getClientList()

        for rental in rentalList:
            client_id = int(rental.clientId)
            appearenceCounter[client_id] = appearenceCounter.get(client_id, 0) + 1

        clientList.sort(key=lambda client: appearenceCounter.get(int(client.clientId), 0), reverse=True)

        for client in clientList:
            print(f"{client.clientId}. {client.name}")

    def manageLateRentals(self):
        listOfLateRentals = self.servicesRental.lateRentals()
        self.servicesRental.sortListOfLateRentalsByTheNumberOfDaysLate(listOfLateRentals)
        for rental in listOfLateRentals:
            print(f"{rental.rentalId}. {rental.clientId}. {rental.movieId}. {rental.rentedDate} / {rental.dueDate} / {rental.returnedDate}")

    def manageUndo(self, stackOfOperations, redoStack):
        if len(stackOfOperations) <= 0:
            raise Exception("No more undos available")
        if "movie" in stackOfOperations[len(stackOfOperations) - 1]["command"].lower():
            self.servicesMovie.manageUndo(stackOfOperations, redoStack)
        elif "client" in stackOfOperations[len(stackOfOperations) - 1]["command"].lower():
            self.servicesClient.manageUndo(stackOfOperations, redoStack)
        elif "rental" in stackOfOperations[len(stackOfOperations) - 1]["command"].lower():
            self.servicesRental.manageUndo(stackOfOperations, redoStack)

    def manageRedo(self, stackOfOperations, redoStack):
        if len(redoStack) <= 0:
            raise Exception("No more redos available")
        if "movie" in redoStack[len(redoStack) - 1]["command"].lower():
            self.servicesMovie.manageRedo(stackOfOperations, redoStack)
        elif "client" in redoStack[len(redoStack) - 1]["command"].lower():
            self.servicesClient.manageRedo(stackOfOperations, redoStack)
        elif "rental" in redoStack[len(redoStack) - 1]["command"].lower():
            self.servicesRental.manageRedo(stackOfOperations, redoStack)