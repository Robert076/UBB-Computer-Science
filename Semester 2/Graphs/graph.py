import random
import time

class Graph:
    def __init__(self, n=0):
        '''Constructs a graph with n vertices numbered from 0 to n-1 and no edges
        '''
        self.n = n
        self.din = {}
        self.dout = {}
        for i in range(0, self.n):
            self.din[i] = []
            self.dout[i] = []
        self.__cost = {}
    def add_vertex(self, x):
        '''Inserts the vertex into the graph.
        Preconditions:
          - x is not already a vertex.
          - x is of a type that can be used as a key in a dictionary (immutable and hashable)
        '''
        if x in self.din.keys() or x in self.dout.keys():
            raise ValueError(f"Vertex {x} already exists!")
        self.din[x] = []
        self.dout[x] = []
    def add_edge(self, x, y, c=1):
        '''Adds an edge from vertex x to vertex y and returns True.
            If the edge already exists, nothing happens and the function returns False.
            Precondition: x and y are valid vertices of the graph.
        '''
        if x in self.din[y] or y in self.dout[x]:
            return False
        self.din[y].append(x)
        self.dout[x].append(y)
        self.__cost[(x,y)] = c
        return True
        
    def cost(self, x, y):
        '''Returns the cost of the edge (x,y).
        Precondition: (x,y) is an edge of the graph
        '''
        return self.__cost[x,y]
    def is_edge(self, x, y):
        '''Returns True if there is an edge from x to y in the graph, and False otherwise.
            Precondition: x and y are valid vertices of the graph.
        '''
        return x in self.din[y]
    def parse_nout(self, x):
        '''Returns something iterable that contains all the outbound neighbors of vertex x
            Precondition: x is a valid vertex of the graph.
        '''
        return list(self.dout[x])
    def parse_nin(self, x):
        '''Returns something iterable that contains all the inbound neighbors of vertex x
            Precondition: x is a valid vertex of the graph.
        '''
        return list(self.din[x])
    def parse_vertices(self):
        '''Return something iterable that contains all the vertices of the graph
        '''
        return list(self.din.keys())

class GraphOnlyOutbound:
    def __init__(self, n=0):
        '''Constructs a graph with n vertices numbered from 0 to n-1 and no edges
        '''
        self.dout = {}
        for i in range(0, n):
            self.dout[i] = []
    def add_vertex(self, x):
        '''Inserts the vertex into the graph.
        Preconditions:
          - x is not already a vertex.
          - x is of a type that can be used as a key in a dictionary (immutable and hashable)
        '''
        if x in self.dout.keys():
            raise ValueError(f"Vertex {x} already exists!")
        self.dout[x] = []
    def add_edge(self, x, y):
        '''Adds an edge from vertex x to vertex y and returns True.
            If the edge already exists, nothing happens and the function returns False.
            Precondition: x and y are valid vertices of the graph.
        '''
        if y in self.dout[x]:
            return False
        self.dout[x].append(y)
        return True
    def is_edge(self, x, y):
        '''Returns True if there is an edge from x to y in the graph, and False otherwise.
            Precondition: x and y are valid vertices of the graph.
        '''
        return y in self.dout[x]
    def parse_nout(self, x):
        '''Returns something iterable that contains all the outbound neighbors of vertex x
            Precondition: x is a valid vertex of the graph.
        '''
        return list(self.dout[x])
    def parse_nin(self, x):
        '''Returns something iterable that contains all the inbound neighbors of vertex x
            Precondition: x is a valid vertex of the graph.
        '''
        result = []
        for y in self.dout.keys():
            if x in self.dout[y]:
                result.append(y)
        return result
    def parse_vertices(self):
        '''Return something iterable that contains all the vertices of the graph
        '''
        return list(self.dout.keys())

class GraphAdjacencyMatrix:
    def __init__(self, n=0):
        '''Constructs a graph with n vertices numbered from 0 to n-1 and no edges
        '''
        self.n = n
        self.matrix = [False for i in range(n*n)]
    def add_vertex(self, x):
        '''Inserts the vertex into the graph.
        Preconditions:
          - x is not already a vertex.
          - x is of a type that can be used as a key in a dictionary (immutable and hashable)
        '''
        raise Exception("Not available")
    def add_edge(self, x, y):
        '''Adds an edge from vertex x to vertex y and returns True.
            If the edge already exists, nothing happens and the function returns False.
            Precondition: x and y are valid vertices of the graph.
        '''
        if self.matrix[x*self.n + y]:
            return False
        self.matrix[x*self.n + y] = True
        return True
    def is_edge(self, x, y):
        '''Returns True if there is an edge from x to y in the graph, and False otherwise.
            Precondition: x and y are valid vertices of the graph.
        '''
        return self.matrix[x*self.n + y]
    def parse_nout(self, x):
        '''Returns something iterable that contains all the outbound neighbors of vertex x
            Precondition: x is a valid vertex of the graph.
        '''
        result = []
        for i in range(self.n):
            if self.matrix[x*self.n+i]:
                result.append(i)
                
        return result
                
    def parse_nin(self, x):
        '''Returns something iterable that contains all the inbound neighbors of vertex x
            Precondition: x is a valid vertex of the graph.
        '''
        result = []
        for i in range(self.n):
            if self.matrix[i*self.n+x]:
                result.append(i)
                
        return result
    def parse_vertices(self):
        '''Return something iterable that contains all the vertices of the graph
        '''
        return range(self.n)

def create_small_graph(ctor):
    g = ctor(5)
    g.add_edge(0, 1, 1)
    g.add_edge(0, 2, 4)
    g.add_edge(1, 2, 2)
    g.add_edge(2, 0, 2)
    g.add_edge(2, 3, 3)
    return g

def create_small_dag(ctor):
    g = ctor(7)
    g.add_edge(0, 3, 1)
    g.add_edge(0, 4, 1)
    g.add_edge(3, 1, 1)
    g.add_edge(4, 1, -1)
    g.add_edge(1, 2, 1)
    g.add_edge(1, 5, 1)
    g.add_edge(2, 6, 1)
    g.add_edge(5, 6, 1)
    g.add_edge(6, 1, 1)
    return g

def create_random_graph(ctor, n, m):
    g = ctor(n)
    while m!=0:
        a = random.randint(0,n-1)
        b= random.randint(0,n-1)
        if not g.is_edge(a,b):
            g.add_edge(a,b)
            m=m-1
            
    return g

def print_graph(g):
    print("Outbound:")
    for x in g.parse_vertices():
        print(f"{x}:", end='')
        for y in g.parse_nout(x):
            print(f" {y}", end='')
        print()
    print("Inbound:")
    for x in g.parse_vertices():
        print(f"{x}:", end='')
        for y in g.parse_nin(x):
            print(f" {y}", end='')
        print()

def measure_time(g):
    print("Outbound:")
    t = time.time()
    for x in g.parse_vertices():
        for y in g.parse_nout(x):
            pass
    print(f"  time={(time.time()-t)*1000} ms")
    print("Inbound:")
    t = time.time()
    for x in g.parse_vertices():
        for y in g.parse_nin(x):
            pass
    print(f"  time={(time.time()-t)*1000} ms")

def test_basic_graph():
    #g = create_small_graph(GraphAdjacencyMatrix)
    g = create_random_graph(GraphOnlyOutbound, 1000, 500000)
    #print_graph(g)
    measure_time(g)

if __name__ == "__main__":
    test_basic_graph()
