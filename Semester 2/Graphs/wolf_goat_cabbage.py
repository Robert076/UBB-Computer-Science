class Vertex:
    def __init__(self, v):
        self.__v = v
    def __repr__(self):
        sides = ([], [])
        for i in range(4):
            name = ["wolf", "goat", "cabbage", "boat"][i]
            value = self.get_side(i)
            sides[value].append(name)
        return f"({','.join(sides[0])}|{','.join(sides[1])})"
    def __str__(self):
        return self.__repr__()
    def __eq__(self, other):
        if not isinstance(other, Vertex):
            return False
        return self.__v == other.__v
    def __hash__(self):
        return self.__v
    def get_side(self, i):
        return 0 if ((self.__v & (1<<i)) == 0) else 1
    def is_valid(self):
        return self.get_side(3) ==self.get_side(1) or (
            self.get_side(1) != self.get_side(0) and self.get_side(1) != self.get_side(2))
        
    def parse_neighbors(self):
        for i in range(4):
            v = self.__v
            if self.get_side(i) == self.get_side(3):
                v ^= (1<<3)
                if i != 3:
                    v ^= (1<< i)
                vertex = Vertex(v)
                if vertex.is_valid():
                    yield vertex

class WolfGoatCabbageGraph:
    def is_edge(self, x, y):
        '''Returns True if there is an edge from x to y in the graph, and False otherwise.
            Precondition: x and y are valid vertices of the graph.
        '''
        raise Exception("Not implemented")
    def parse_nout(self, x):
        '''Returns something iterable that contains all the outbound neighbors of vertex x
            Precondition: x is a valid vertex of the graph.
        '''
        return x.parse_neighbors()
    def parse_nin(self, x):
        '''Returns something iterable that contains all the inbound neighbors of vertex x
            Precondition: x is a valid vertex of the graph.
        '''
        return x.parse_neighbors()
    def parse_vertices(self):
        '''Return something iterable that contains all the vertices of the graph
        '''
        raise Exception("Not implemented")
    def initial_state(self):
        return Vertex(0)
    def final_state(self):
        return Vertex(15)
