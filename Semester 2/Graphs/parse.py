import graph
import wolf_goat_cabbage

import heapq
import math

def bfs(g, s):
    '''Returns a tuple of three dictionaries: prev, dist and children, each having as keys all
    the accessible vertices from s and the values are: the parent (None for the root), the distance
    from s (which is the level in the tree) and the list of children
    '''
    queue = [s]
    prev = {s: None}
    dist = {s: 0}
    children = {s:[]}
    while len(queue)>0:
        x = queue[0]
        queue.pop(0) # Warning: this is expensive!
        
        nout = g.parse_nout(x)
        for y in nout:
            if y not in prev.keys():
                prev[y] = x;
                dist[y] = dist[x] + 1
                children[y] = []
                children[x].append(y)
                queue.append(y)
    
    return prev, dist, children

def shortest_path(g, s, t):
    prev,dist,children = bfs(g, s)
    print(prev, s, t)
    if t not in prev:
        return None
    v = t
    result = []
    while (v != s):
        result.append(v)
        v = prev[v]
    result.append(s)
    result.reverse()
    return result

def dijkstra(g, s):
    '''Returns a tuple of two dictionaries: prev and dist, each having as keys all
    the accessible vertices from s and the values are: the parent (None for the root) and the distance
    from s
    '''
    queue = [(0,s)]
    prev = {s: None}
    dist = {s: 0}
    while len(queue)>0:
        dx,x = heapq.heappop(queue)

        nout = g.parse_nout(x)
        for y in nout:
            if y not in prev.keys() or dist[x] + g.cost(x,y) < dist[y]:
                prev[y] = x;
                dist[y] = dist[x] + g.cost(x,y)
                heapq.heappush(queue, (dist[y],y))
    
    return prev, dist

def min_cost_path_dijkstra(g, s, t):
    prev, dist = dijkstra(g, s)
    print(f"prev={prev}")
    print(f"dist={dist}")
    if t not in prev:
        return None
    v = t
    result = []
    while (v != s):
        result.append(v)
        v = prev[v]
    result.append(s)
    result.reverse()
    return result

def dp_find_min_cost(g, s):
    '''Returns a tuple (w, prev) where:
     - w is list of dictionaries w such that w[k][x] = the cost of min cost walk from s to x
        of length = k, or math.inf . k ranges from 0 to n, where n is the number of vertices of g
     - prev is a list of dictionaries such that prev[k][x] = the previous vertex, before x, on the min cost
        walk from s to x of length k
    '''
    all_vertices = g.parse_vertices()
    w = [{x:math.inf for x in all_vertices}]
    w[0][s] = 0
    prev = [{s:None}]
    for k in range(len(all_vertices)):
        current_row = w[k]
        next_row = {}
        prev.append({})
        for x in all_vertices:
            next_row[x] = math.inf
            for y in g.parse_nin(x):
                if next_row[x] > current_row[y] + g.cost(y, x):
                    next_row[x] = current_row[y] + g.cost(y, x)
                    prev[k+1][x] = y
        w.append(next_row)
    return w,prev

def min_cost_path_dp(g, s, t):
    w,prev = dp_find_min_cost(g, s)
    print(f"w={w}")
    print(f"prev={prev}")
    min_cost = math.inf
    len_min_cost = None
    for k in range(len(w)):
        if w[k][t] < min_cost:
            min_cost = w[k][t]
            len_min_cost = k
    if len_min_cost is None:
        return None
    current_vertex = t
    current_len = len_min_cost
    path = []
    while current_len > 0:
        path.append(current_vertex)
        current_vertex = prev[current_len][current_vertex]
        current_len -= 1
    assert(current_vertex == s)
    path.append(s)
    path.reverse()
    return path

def matrix_multiplication(g):
    '''Returns a dictionary w such that, for any pair of vertices (x,y), w[x,y] = cost of min cost path from
    x to y
    '''
    n = len(list(g.parse_vertices()))
    w = {}
    prev = {}
    for x in g.parse_vertices():
        for y in g.parse_vertices():
            if x == y:
                w[x,y] = 0
                prev[x,y] = x
            elif g.is_edge(x, y):
                w[x,y] = g.cost(x,y)
                prev[x,y] = x
            else:
                w[x,y] = math.inf
    k = 1
    while k < n:
        next_w = {}
        next_p = {}
        for x in g.parse_vertices():
            for y in g.parse_vertices():
                next_w[x,y] = math.inf
                for z in g.parse_vertices():
                    if next_w[x,y] > w[x,z] + w[z,y]:
                        next_w[x,y] = w[x,z] + w[z,y]
                        next_p[x,y] = prev[z,y]
        
        k = k * 2
        w = next_w
        prev = next_p
    return w, prev

def min_cost_path_matrix_multiplication(g, s, t):
    print(matrix_multiplication(g))

def toposort(g):
    '''Returns a topologically sorted list of all vertices of graph g,
    or None if g has cycles
    '''
    no_predecesors = []
    result = []
    in_degree = {}
    for v in g.parse_vertices():
        in_degree[v] = len(g.parse_nin(v))
        if (len(g.parse_nin(v)) == 0):
            no_predecesors.append(v)
    
    
    while (len(no_predecesors) > 0):
        v = no_predecesors.pop(0)
        for o in g.parse_nout(v):
            in_degree[o] -= 1
            if (in_degree[o] == 0):
                no_predecesors.append(o)
        result.append(v)
    
    if (len(g.parse_vertices()) != len(result)):
        print("we have a cycle")
        return None
    else:
        return result

def dag_find_min_cost(g, s):
    '''Returns a tuple (dist, prev) where:
     - dist is a dictionary dist[x] = the cost of min cost walk from s to x
        or math.inf 
     - prev is a dictionary such that prev[x] = the previous vertex, before x, on the min cost
        walk from s to x. prev[s] = None, and prev[x] does not exist for vertices x not accessible from s
    '''
    sorted_list = toposort(g)
    dist = {s: 0}
    prev = {s:None}

    for x in sorted_list:
        if x != s:
            dist[x] = math.inf
        for y in g.parse_nin(x):
            if dist[x] > dist[y] + g.cost(y, x):
                dist[x] = dist[y] + g.cost(y, x)
                prev[x] = y

    return dist,prev

def find_cost_dag_local(g, s, x, prev_tuples):
    '''Computes and returns the cost of min cost walk from the starting vertex to vertex x, given:
      - g = the graph
      - s = starting vertex
      - x = destination vertex
      - prev_tuples = a list of tuples (y, dy) where y is a predecessor of x and dy is the cost of
          min cost walk from s to y; prev_tuples contains all predecessors of x
    '''
    if x == s:
        print(f"find_cost_dag_local({x})->0")
        return 0
    dist = math.inf
    for y,dy in prev_tuples:
        if dist > dy + g.cost(y, x):
            dist = dy + g.cost(y, x)
    print(f"find_cost_dag_local({x})->{dist}")
    return dist

def find_cost_dag_internal(g, s, t, cache):
    if t in cache.keys():
        return cache[t]
    prev_tuples = []
    for y in g.parse_nin(t):
        prev_tuples.append((y, find_cost_dag_internal(g, s, y, cache)))
    cache[t] = find_cost_dag_local(g, s, t, prev_tuples)
    return cache[t]

def find_cost_dag(g, s, t):
    cache = {}
    return find_cost_dag_internal(g, s, t, cache)
    
    

def min_cost_path_dag(g, s, t):
    dist,prev = dag_find_min_cost(g, s)
    print(f"dist={dist}")
    print(f"prev={prev}")
    if t not in prev:
        return None
    v = t
    result = []
    while (v != s):
        result.append(v)
        v = prev[v]
    result.append(s)
    result.reverse()
    return result

def test_bfs():
    g = graph.create_small_graph(graph.Graph)
    print(shortest_path(g, 3, 0))

def wolf_goat_cabbage_test():
    g = wolf_goat_cabbage.WolfGoatCabbageGraph()
    s = g.initial_state()
    t = g.final_state()
    print(shortest_path(g, s, t))

def test_dijkstra():
    g = graph.create_small_graph(graph.Graph)
    print(min_cost_path_dijkstra(g, 0, 3))

def test_dp_min_cost_path():
    g = graph.create_small_graph(graph.Graph)
    print(min_cost_path_dp(g, 0, 3))

def test_matrix_multiplication():
    g = graph.create_small_graph(graph.Graph)
    print(min_cost_path_matrix_multiplication(g, 0, 3))

def test_dag():
    g = graph.create_small_dag(graph.Graph)
    #print(toposort(g))
    print(find_cost_dag(g, 0, 6))

def test_dag2():
    g = graph.create_small_dag(graph.Graph)
    print(min_cost_path_dag(g, 0, 6))

if __name__ == "__main__":
    test_dag()
