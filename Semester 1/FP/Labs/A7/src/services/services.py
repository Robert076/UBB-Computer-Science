import unittest
class Services:
    def __init__(self, repository):
        self.repository = repository

    def addStudent(self, id, name, group):
        """
        Function that adds a student to the list
        :param id: the id of the newly created student
        :param name: the name of the newly created student
        :param group: the group of the newly created student
        :return:
        """
        studentsList = self.repository.getStudentsList()
        student = self.repository.createStudent(id, name, group)
        studentsList.append(student)
        self.repository.setStudentsList(studentsList)



    def deleteStudentsFromGivenGroup(self, givenGroup):
        list = self.repository.getStudentsList()
        stackOfOperations = self.repository.getStackOfOperations()

        studentPosition = int(0)

        while studentPosition < len(list):
            if list[studentPosition].group == givenGroup:
                stackOfOperations.append(list[studentPosition])
                list.remove(list[studentPosition])
                studentPosition -= 1
            studentPosition += 1
        self.repository.setStudentsList(list)

    def undoLastOperation(self):
        newStackOfOperations = []
        stackOfOperations = self.repository.getStackOfOperations()

        if len(stackOfOperations) == 0:
            raise Exception("No more undos available")

        studentsList = self.repository.getStudentsList()
        lastDeletedGroup = stackOfOperations[len(stackOfOperations) - 1].group

        for student in stackOfOperations:
            if student.group == lastDeletedGroup:
                studentsList.append(student)
            else:
                newStackOfOperations.append(student)

        self.repository.setStackOfOperations(newStackOfOperations)
        self.repository.setStudentsList(studentsList)

from src.repository.repository import MemoryRepository
from src.domain.domain import Student

class TestAddStudent(unittest.TestCase):

    def setUp(self):
        self.repository = MemoryRepository(Student)
        self.services = Services(self.repository)

    def testAddStudent(self):
        self.services.addStudent(11, "Dani Mocanu", "420")
        studentsList = self.repository.getStudentsList()
        self.assertEqual(len(studentsList), 11)
        self.assertEqual(studentsList[10].id, 11)
        self.assertEqual(studentsList[10].name, "Dani Mocanu")
        self.assertEqual(studentsList[10].group, "420")

        self.services.addStudent(12, "Tzanca Uraganu", "69")
        studentsList = self.repository.getStudentsList()
        self.assertEqual(len(studentsList), 12)
        self.assertEqual(studentsList[11].id, 12)
        self.assertEqual(studentsList[11].name, "Tzanca Uraganu")
        self.assertEqual(studentsList[11].group, "69")

if __name__ == '__main__':
    unittest.main()