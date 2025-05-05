from random import randint
import queue


class DirectedGraph:
    def __init__(self):
        self.vertices = set()
        self.outbound_edges = {}  # Outbound edges indexed by source vertex
        self.inbound_edges = {}   # Inbound edges indexed by target vertex
        self.edge_costs = {}      # Edge costs indexed by (source, target) tuple

    def add_vertex(self, vertex):
        self.vertices.add(vertex)

    def remove_vertex(self, vertex):
        self.vertices.discard(vertex)
        self.outbound_edges.pop(vertex, None)
        self.inbound_edges.pop(vertex, None)

    def add_edge(self, source, target, cost):
        self.add_vertex(source)
        self.add_vertex(target)
        self.outbound_edges.setdefault(source, []).append(target)
        self.inbound_edges.setdefault(target, []).append(source)
        self.edge_costs[(source, target)] = cost

    def remove_edge(self, source, target):
        if source in self.outbound_edges:
            self.outbound_edges[source] = [t for t in self.outbound_edges[source] if t != target]
        if target in self.inbound_edges:
            self.inbound_edges[target] = [s for s in self.inbound_edges[target] if s != source]
        self.edge_costs.pop((source, target), None)

    def get_num_vertices(self):
        return len(self.vertices)

    def parse_vertices(self):
        return iter(self.vertices)

    def has_edge(self, source, target):
        return target in (t for t in self.outbound_edges.get(source, []))

    def get_edge_cost(self, source, target):
        return self.edge_costs.get((source, target), None)

    def set_edge_cost(self, source, target, cost):
        self.edge_costs[(source, target)] = cost

    def get_out_degree(self, vertex):
        return len(self.outbound_edges.get(vertex, []))

    def get_in_degree(self, vertex):
        return len(self.inbound_edges.get(vertex, []))

    def parse_outbound_edges(self, vertex):
        return iter(self.outbound_edges.get(vertex, []))

    def parse_inbound_edges(self, vertex):
        return iter(self.inbound_edges.get(vertex, []))

    def get_endpoints(self, source, target):
        return source, target

    def copy(self):
        new_graph = DirectedGraph()
        new_graph.vertices = self.vertices.copy()
        new_graph.outbound_edges = {v: edges[:] for v, edges in self.outbound_edges.items()}
        new_graph.inbound_edges = {v: edges[:] for v, edges in self.inbound_edges.items()}
        new_graph.edge_costs = self.edge_costs.copy()
        return new_graph

    def read_graph_from_file(self, filename):
        with open(filename, 'r') as file:
            parts = file.readline().strip().split()
            vertices = int(parts[0])
            edges = int(parts[1])
            for line in file:
                parts = line.strip().split()
                source = int(parts[0])
                target = int(parts[1])
                cost = int(parts[2])
                self.add_edge(source, target, cost)

    def write_graph_to_file(self, filename):
        with open(filename, 'w') as file:
            for source, edges in self.outbound_edges.items():
                for target in edges:
                    cost = self.edge_costs[(source, target)]
                    file.write(f"{source} {target} {cost}\n")

    def bfs(self, start_vertex):
        q = []
        visited = [False] * (self.get_num_vertices() + 1)
        q.append(start_vertex)
        visited[start_vertex] = True
        path = [None] * (self.get_num_vertices() + 1)
        while len(q) > 0:
            node = q.pop(0)
            neighbours = [v for v in self.outbound_edges.get(node, [])]
            if len(neighbours) > 0:
                for v in neighbours:
                    if not visited[v]:
                        q.append((v))
                        visited[v] = True
                        path[v] = node
        return path

    def get_shortest_path(self, start_vertex, end_vertex):
        path = self.bfs(start_vertex)
        final_path = [end_vertex]
        position = end_vertex
        while path[position] != None:
            final_path.append(path[position])
            position = path[position]
        final_path.reverse()
        print(final_path, "Length:", len(final_path) - 1)

    def get_lowest_cost_walk(self, start_vertex, end_vertex):
        num_vertices = self.get_num_vertices()
        inf = float('inf')
        max_vertex_index = max(max(self.outbound_edges.keys()), max([target for edges in self.outbound_edges.values() for target in edges]))
        num_vertices = max_vertex_index + 1
        cost_matrix = [[inf] * num_vertices for _ in range(num_vertices)]
        next_vertex_matrix = [[None] * num_vertices for _ in range(num_vertices)]

        for source, edges in self.outbound_edges.items():
            for target in edges:
                cost_matrix[source][target] = self.edge_costs[(source, target)]
                next_vertex_matrix[source][target] = target
        for i in range(num_vertices):
            cost_matrix[i][i] = 0

        for k in range(num_vertices):
            new_cost_matrix = [[inf] * num_vertices for _ in range(num_vertices)]
            new_next_vertex_matrix = [[None] * num_vertices for _ in range(num_vertices)]
            for i in range(num_vertices):
                for j in range(num_vertices):
                    new_cost_matrix[i][j] = min(cost_matrix[i][j], cost_matrix[i][k] + cost_matrix[k][j])
                    if cost_matrix[i][k] + cost_matrix[k][j] < cost_matrix[i][j]:
                        new_next_vertex_matrix[i][j] = next_vertex_matrix[i][k]
                    else:
                        new_next_vertex_matrix[i][j] = next_vertex_matrix[i][j]
            cost_matrix = new_cost_matrix
            next_vertex_matrix = new_next_vertex_matrix

        for i in range(num_vertices):
            if cost_matrix[i][i] < 0:
                print("Negative cost cycle detected!")
                return
        if cost_matrix[start_vertex][end_vertex] == inf:
            print("No path exists between the given vertices.")
            return
        path = [str(start_vertex)]
        current_vertex = start_vertex
        while current_vertex != end_vertex:
            next_vertex_index = next_vertex_matrix[current_vertex][end_vertex]
            if next_vertex_index is None:
                break
            path.append(str(next_vertex_index))
            current_vertex = next_vertex_index
        min_cost = cost_matrix[start_vertex][end_vertex]
        print(f"The lowest cost walk from {start_vertex} to {end_vertex} is: {' -> '.join(path)}, Cost: {min_cost}")

    def find_lowest_cost_walk(self, start_vertex, end_vertex):
        num_vertices = self.get_num_vertices()
        inf = float('inf')
        max_vertex_index = max(max(self.outbound_edges.keys()), max([target for edges in self.outbound_edges.values() for target in edges]))
        num_vertices = max_vertex_index + 1
        cost_matrix = [[inf] * num_vertices for _ in range(num_vertices)]
        next_vertex_matrix = [[None] * num_vertices for _ in range(num_vertices)]

        for source, edges in self.outbound_edges.items():
            for target in edges:
                cost_matrix[source][target] = self.edge_costs[(source, target)]
                next_vertex_matrix[source][target] = target
        for i in range(num_vertices):
            cost_matrix[i][i] = 0

        for k in range(num_vertices):
            new_cost_matrix = [[inf] * num_vertices for _ in range(num_vertices)]
            new_next_vertex_matrix = [[None] * num_vertices for _ in range(num_vertices)]
            for i in range(num_vertices):
                for j in range(num_vertices):
                    new_cost_matrix[i][j] = min(cost_matrix[i][j], cost_matrix[i][k] + cost_matrix[k][j])
                    if cost_matrix[i][k] + cost_matrix[k][j] < cost_matrix[i][j]:
                        new_next_vertex_matrix[i][j] = next_vertex_matrix[i][k]
                    else:
                        new_next_vertex_matrix[i][j] = next_vertex_matrix[i][j]
            
            cost_matrix = new_cost_matrix
            next_vertex_matrix = new_next_vertex_matrix

        for i in range(num_vertices):
            if cost_matrix[i][i] < 0:
                print("Negative cost cycle detected!")
                return
        if cost_matrix[start_vertex][end_vertex] == inf:
            print("No path exists between the given vertices.")
            return
        path = [str(start_vertex)]
        current_vertex = start_vertex
        while current_vertex != end_vertex:
            next_vertex_index = next_vertex_matrix[current_vertex][end_vertex]
            if next_vertex_index is None:
                break
            path.append(str(next_vertex_index))
            current_vertex = next_vertex_index
        min_cost = cost_matrix[start_vertex][end_vertex]
        print(f"The lowest cost walk from {start_vertex} to {end_vertex} is: {' -> '.join(path)}, Cost: {min_cost}")

    def topological_sort(self):
        in_degree = {vertex: 0 for vertex in self.vertices}
        for source in self.outbound_edges:
            for target in self.outbound_edges[source]:
                in_degree[target] += 1

        queue = [vertex for vertex in in_degree if in_degree[vertex] == 0]
        count = 0
        sorted_vertices = []

        while queue:
            current = queue.pop(0)
            count += 1
            sorted_vertices.append(current)
            for neighbor in self.outbound_edges.get(current, []):
                in_degree[neighbor] -= 1
                if in_degree[neighbor] == 0:
                    queue.append(neighbor)

        if count != len(self.vertices):
            raise ValueError("The graph contains cycles and is not a Directed Acyclic Graph (DAG).")
        else:
            return sorted_vertices


    def bonus_lab4_p2(self, x, y):
        #verify if the corresponding graph is a DAG and performs a topological sorting of the activities;
        #if it is a DAG, finds the number of distinct paths between two given vertices, in O(m+n).

  
        
        sorted_vertices = self.topological_sort()
        num_vertices = self.get_num_vertices()
        num_paths = [0] * num_vertices
        num_paths[x] = 1

        for vertex in sorted_vertices:
            for neighbor in self.outbound_edges.get(vertex, []):
                num_paths[neighbor] += num_paths[vertex]

        return num_paths[y]
    
def create_random_graph(number_of_vertices, number_of_edges):
    graph = DirectedGraph()
    vertices = 0
    while vertices < number_of_vertices:
        if graph.add_vertex(randint(0, number_of_vertices)) != False:
            vertices += 1
    edges = 0
    while edges < number_of_edges:
        graph.add_edge(randint(0, number_of_vertices), randint(0, number_of_vertices), randint(0, 100))
        edges += 1
    return graph



def print_menu():
    print("1. Read a graph from a file")
    print("17. Find the lowest length path between 2 vertices")
    print("18. Find the lowest cost walk between 2 vertices")
    print("19. Topological sort")
    print("20. Bonus")
    print("30. Exit")


def run_menu():
    read_graph_option = "1"
    write_graph_option = "2"
    get_num_vertices_option = "3"
    parse_vertices_option = "4"
    is_edge_option = "5"
    get_vertex_degrees_option = "6"
    parse_outbound_edges_option = "7"
    parse_inbound_edges_option = "8"
    retrieve_edge_info_option = "9"
    modify_edge_info_option = "10"
    add_edge_option = "11"
    remove_edge_option = "12"
    add_vertex_option = "13"
    remove_vertex_option = "14"
    copy_graph_option = "15"
    create_random_graph_option = "16"
    lowest_length_path = "17"
    lowest_cost_walk = "18"
    topological_sort = "19"
    lab4_bonus1 = "20"
    lab4_bonus2 = "21"
    lab4_bonus3 = "22"
    exit_option = "30"
    graph = DirectedGraph()
    while True:
        print_menu()
        user_choice = input("Enter you choice: ")
        if user_choice == read_graph_option:
            filename = input("Enter the filename: ")
            graph.read_graph_from_file(filename)
        elif user_choice == write_graph_option:
            graph.write_graph_to_file('graph_out.txt')
        elif user_choice == get_num_vertices_option:
            print("There are ", graph.get_num_vertices(), " vertices")
        elif user_choice == parse_vertices_option:
            print("Vertices:", list(graph.parse_vertices()))
        elif user_choice == is_edge_option:
            vertex_1 = int(input("Enter the first vertex: "))
            vertex_2 = int(input("Enter the second vertex: "))
            is_edge = graph.has_edge(vertex_1, vertex_2)
            if is_edge:
                print(f"There is an edge from {vertex_1} to {vertex_2}")
            else:
                print(f"There isn't an edge from {vertex_1} to {vertex_2}")
        elif user_choice == get_vertex_degrees_option:
            vertex = int(input("Enter a vertex: "))
            print("In degree of the given vertex is ", graph.get_in_degree(vertex))
            print("Out degree of the given vertex is ", graph.get_out_degree(vertex))
        elif user_choice == parse_outbound_edges_option:
            vertex = int(input("Enter the vertex: "))
            print("Outbound edges:", list(graph.parse_outbound_edges(vertex)))
        elif user_choice == parse_inbound_edges_option:
            vertex = int(input("Enter the vertex: "))
            print("Inbound edges:", list(graph.parse_inbound_edges(vertex)))
        elif user_choice == retrieve_edge_info_option:
            vertex_1 = int(input("Enter the first vertex: "))
            vertex_2 = int(input("Enter the second vertex: "))
            print(f"The edge cost is {graph.get_edge_cost(vertex_1, vertex_2)}")
        elif user_choice == modify_edge_info_option:
            vertex_1 = int(input("Enter the first vertex: "))
            vertex_2 = int(input("Enter the second vertex: "))
            cost = int(input("Enter the new cost: "))
            graph.set_edge_cost(vertex_1, vertex_2, cost)
        elif user_choice == add_edge_option:
            vertex_1 = int(input("Enter the first vertex: "))
            vertex_2 = int(input("Enter the second vertex: "))
            cost = int(input("Enter the new cost: "))
            graph.add_edge(vertex_1, vertex_2, cost)
        elif user_choice == remove_edge_option:
            vertex_1 = int(input("Enter the first vertex: "))
            vertex_2 = int(input("Enter the second vertex: "))
            graph.remove_edge(vertex_1, vertex_2)
        elif user_choice == add_vertex_option:
            vertex = int(input("Enter the vertex: "))
            graph.add_vertex(vertex)
        elif user_choice == remove_vertex_option:
            vertex = int(input("Enter the vertex: "))
            graph.remove_vertex(vertex)
        elif user_choice == copy_graph_option:
            new_graph = graph.copy()
        elif user_choice == create_random_graph_option:
            number_of_vertices = int(input("Enter the number of vertices: "))
            number_of_edges = int(input("Enter the number of edges: "))
            graph = create_random_graph(number_of_vertices, number_of_edges)
        elif user_choice == lowest_length_path:
            start_vertex = int(input("Enter the start vertex: "))
            end_vertex = int(input('Enter the end vertex: '))
            graph.get_shortest_path(start_vertex, end_vertex)
        elif user_choice == lowest_cost_walk:
            start_vertex = int(input("Enter the start vertex: "))
            end_vertex = int(input('Enter the end vertex: '))
            graph.find_lowest_cost_walk(start_vertex, end_vertex)
        elif user_choice == topological_sort:
            try:
                print("Topological sort:", graph.topological_sort())
            except ValueError as e:
                print(e)
        elif user_choice == lab4_bonus1:
            x = int(input("Enter the first vertex: "))
            y = int(input("Enter the second vertex: "))
            print(f"The number of distinct paths between {x} and {y} is {graph.bonus_lab4_p2(x, y)}")
        elif user_choice == lab4_bonus2:
            pass
        elif user_choice == lab4_bonus3:
            pass
        elif user_choice == exit_option:
            print("Goodbye!")
            return
        else:
            print("Invalid input!")


run_menu()
