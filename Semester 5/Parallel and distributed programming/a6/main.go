package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var (
	finalResult []int
	found       = false
	mtx         sync.Mutex
)

func solve(adj [][]int, path []int, visited []bool, threads int) {
	mtx.Lock()
	if found {
		mtx.Unlock()
		return
	}
	mtx.Unlock()

	current := path[len(path)-1]

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

	var neighbors []int
	for _, n := range adj[current] {
		if !visited[n] {
			neighbors = append(neighbors, n)
		}
	}

	if len(neighbors) == 0 {
		return
	}

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
				wg.Add(1)
				go func(node int, tCount int) {
					defer wg.Done()
					newPath := append(append([]int{}, path...), node)
					newVisited := append([]bool{}, visited...)
					newVisited[node] = true
					solve(adj, newPath, newVisited, tCount)
				}(next, assigned)
			} else {
				runSequential(adj, path, visited, next)
			}
		}
		wg.Wait()
	} else {
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
	// graph := [][]int{
	// 	{1, 2, 3},
	// 	{0, 2, 3},
	// 	{0, 1, 3},
	// 	{0, 1, 2},
	// }

	n := 200
	graph := make([][]int, n)
	for i := 0; i < n; i++ {
		for j := 0; j < n; j++ {
			if i != j && rand.Int()%10 == 0 {
				graph[i] = append(graph[i], j)
			}
		}
	}

	visited := make([]bool, len(graph))
	visited[0] = true

	time1 := time.Now()
	solve(graph, []int{0}, visited, 2)
	time2 := time.Now()

	fmt.Printf("Execution time: %v\n", time2.Sub(time1))

	if finalResult != nil {
		fmt.Println("Found Hamiltonian Cycle:", finalResult)
	} else {
		fmt.Println("No Hamiltonian Cycle found.")
	}
}
