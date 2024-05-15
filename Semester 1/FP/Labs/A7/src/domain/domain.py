class Student:
    def __init__(self, id, name, group):
        self.id = id
        self.name = name
        self.group = group

    def getId(self):
        return self.id

    def getName(self):
        return self.name

    def getGroup(self):
        return self.group