package main

import (
	"fmt"
	"sync"
)

var (
	finalResult []int
	found       = false
	mtx         sync.Mutex
)

func solve(adj [][]int, path []int, visited []bool, threads int) {
	// 1. Quick exit if solution already found
	mtx.Lock()
	if found {
		mtx.Unlock()
		return
	}
	mtx.Unlock()

	current := path[len(path)-1]

	// 2. Base case: Path is complete
	if len(path) == len(adj) {
		for _, neighbor := range adj[current] {
			if neighbor == path[0] {
				mtx.Lock()
				if !found {
					found = true
					finalResult = append([]int{}, path...)
				}
				mtx.Unlock()
				return
			}
		}
		return
	}

	// 3. Find available neighbors
	var neighbors []int
	for _, n := range adj[current] {
		if !visited[n] {
			neighbors = append(neighbors, n)
		}
	}

	if len(neighbors) == 0 {
		return
	}

	// 4. Parallel vs Sequential Search
	if threads > 1 {
		tPerN := threads / len(neighbors)
		extra := threads % len(neighbors)

		var wg sync.WaitGroup

		for i, next := range neighbors {
			assigned := tPerN
			if i < extra {
				assigned++
			}

			if assigned > 0 {
				// Spawn parallel branch
				wg.Add(1)
				go func(node int, tCount int) {
					defer wg.Done()
					newPath := append(append([]int{}, path...), node)
					newVisited := append([]bool{}, visited...)
					newVisited[node] = true
					solve(adj, newPath, newVisited, tCount)
				}(next, assigned)
			} else {
				// No threads left? Run remaining neighbors sequentially
				runSequential(adj, path, visited, next)
			}
		}
		wg.Wait()
	} else {
		// Purely sequential search
		for _, next := range neighbors {
			runSequential(adj, path, visited, next)
		}
	}
}

func runSequential(adj [][]int, path []int, visited []bool, next int) {
	mtx.Lock()
	if found {
		mtx.Unlock()
		return
	}
	mtx.Unlock()

	visited[next] = true
	solve(adj, append(path, next), visited, 1)
	visited[next] = false
}

func main() {
	// Example: A small graph where 0-1-2-3-0 is a cycle
	graph := [][]int{
		{1, 2, 3},
		{0, 2, 3},
		{0, 1, 3},
		{0, 1, 2},
	}

	visited := make([]bool, len(graph))
	visited[0] = true

	// Start the search with 8 threads
	solve(graph, []int{0}, visited, 8)

	if finalResult != nil {
		fmt.Println("Found Hamiltonian Cycle:", finalResult)
	} else {
		fmt.Println("No Hamiltonian Cycle found.")
	}
}
