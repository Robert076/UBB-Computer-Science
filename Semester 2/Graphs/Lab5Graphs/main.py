class Graph:
    def __init__(self, vertices):
        """
        Constructor for the graph class.
        :param vertices: number of vertices in the graph
        return: None
        """
        self.vertices = vertices
        self.graph = [[None for _ in range(vertices)] for _ in range(vertices)]
    
    def addEdge(self, x, y, cost):
        """
        Add an edge to the graph.
        :param x: vertex 1
        :param y: vertex 2
        :param cost: cost of the edge
        return: None
        """
        self.graph[x][y] = cost
        self.graph[y][x] = cost
        
    def hamiltonianCycleUtil(self, path, pos, visited):
        if len(path) == self.vertices:
            if self.graph[path[-1]][path[0]] is not None:
                return path + [path[0]]  
            else:
                return None
        
        
        
        for v in range(self.vertices):
            if self.graph[path[pos - 1]][v] is not None and not visited[v]:
                visited[v] = True
                path.append(v)
                result = self.hamiltonianCycleUtil(path, pos + 1, visited)
                if result is not None:
                    return result
                visited[v] = False
                path.pop()
        
        return None

    def findHamiltonianCycle(self):
        path = [0]
        visited = [False] * self.vertices
        visited[0] = True
        return self.hamiltonianCycleUtil(path, 1, visited)
        
        
        

def runApp():
    while True:
        try:
            filename = input("Please enter the filename (EXIT to exit): ")
            if(filename == "EXIT"):
                exit()
            with open(filename, 'r') as file:
                vertices = int(file.readline())
                graph = Graph(vertices)
                for line in file:
                    x, y, cost = map(int, line.split())
                    graph.addEdge(x, y, cost)
                print(graph.findHamiltonianCycle())
        except FileNotFoundError:
            print("File not found. Please enter a valid filename.")
        except ValueError:
            print("Invalid data format in the file. Please make sure the file format is correct.")

runApp()
