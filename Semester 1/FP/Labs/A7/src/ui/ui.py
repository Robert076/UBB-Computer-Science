class UI:
    def __init__(self, services):
        self.services = services

    def runMenu(self):
        while True:
            print("Please pick an option")
            print("1. Add a student")
            print("2. Display the list of students")
            print("3. Filter the list so that students in a given group (read from the console) are deleted from the list.")
            print("4. Undo the last operation that modified program data")
            print("5. Exit the app")

            userInput = int(input("Enter your choice: "))

            if userInput == 1:
                self.manageAddStudent()
            elif userInput == 2:
                self.managePrintStudentList()
            elif userInput == 3:
                self.manageDeleteStudentsFromGivenGroup()
            elif userInput == 4:
                self.manageUndo()
            elif userInput == 5:
                raise Exception("Goodbye")

    def manageAddStudent(self):
        id = int(input("Enter the id of the student: "))
        name = input("Enter the name of the student: ")
        group = int(input("Enter the group of the student: "))
        self.services.addStudent(id, name, group)

    def managePrintStudentList(self):
        list = self.services.repository.getStudentsList()
        for student in list:
            print(f"{student.id} {student.name} {student.group}")

    def manageDeleteStudentsFromGivenGroup(self):
        group = int(input("What group do you want to delete? "))
        self.services.deleteStudentsFromGivenGroup(group)

    def manageUndo(self):
        self.services.undoLastOperation()