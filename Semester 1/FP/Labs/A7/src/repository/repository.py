import os.path
import pickle


class Repository:
    def __init__(self, domain):
        self.studentsList = []
        self.studentsList.append(domain(1, "Roby", 912))
        self.studentsList.append(domain(2, "Robert", 911))
        self.studentsList.append(domain(3, "Andrei", 921))
        self.studentsList.append(domain(4, "Bogdan", 914))
        self.studentsList.append(domain(5, "Marius", 915))
        self.studentsList.append(domain(6, "Catalin", 961))
        self.studentsList.append(domain(7, "David", 971))
        self.studentsList.append(domain(8, "Dragos", 913))
        self.studentsList.append(domain(9, "Marian", 911))
        self.studentsList.append(domain(10, "Rafael", 911))

        self.domain = domain

    stackOfOperations = []
    studentsRemoved = []

    def getStackOfOperations(self):
        return self.stackOfOperations

    def getStudentsRemoved(self):
        return self.studentsRemoved

    def createStudent(self, id, name, group):
        student = self.domain(id, name, group)
        return student

    def setStackOfOperations(self, newStackOfOperations):
        self.stackOfOperations = newStackOfOperations


class MemoryRepository(Repository):
    def getStudentsList(self):
        return self.studentsList

    def setStudentsList(self, list):
        self.studentsList = list

class BinaryFileRepository(Repository):
    fileName = "binaryFileRepository.pickle"

    def __init__(self, domain):
        super().__init__(domain)
        if not os.path.exists(self.fileName):
            self.setStudentsList(self.studentsList)
        self.getStudentsList()

    def setStudentsList(self, list):
        with open("BinaryFileRepository.pickle", "wb") as binaryRepo:
            pickle.dump(list, binaryRepo)

    def getStudentsList(self):
        with open("BinaryFileRepository.pickle", "rb") as binaryRepo:
            studentsList = pickle.load(binaryRepo)
        return studentsList

class TextFileRepository(Repository):
    fileName = "TextFileRepository.txt"

    def __init__(self, domain):
        super().__init__(domain)
        if not os.path.exists(self.fileName):
            self.setStudentsList(self.studentsList)
        self.getStudentsList()

    def setStudentsList(self, studentsList):
        with open("TextFileRepository.txt", "w") as textRepo:
            for student in studentsList:
                textRepo.write(str(student.getId()) + "," + str(student.getName()) + "," + str(student.getGroup()) + "\n")

    def getStudentsList(self):
        studentsList = []
        with open("TextFileRepository.txt", "r") as textRepo:
            for line in textRepo.readlines():
                line = line.strip()
                id, name, group = line.split(maxsplit=2, sep=",")
                student = self.domain(str(id), str(name), str(group))
                studentsList.append(student)
        return studentsList