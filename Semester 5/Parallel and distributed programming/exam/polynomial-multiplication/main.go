package main

import (
	"fmt"
	"sync"
)

func compute(startIdx, chunkSize int, poly1, poly2, result []int, mu *sync.Mutex, wg *sync.WaitGroup) {
	defer wg.Done()

	for i := startIdx; i < startIdx+chunkSize; i++ {
		for j := 0; j < len(poly2); j++ {
			val := poly1[i] * poly2[j]

			mu.Lock()
			result[i+j] += val
			mu.Unlock()
		}
	}
}

func main() {
	var size1 int
	fmt.Print("Size of the first polynomial: ")
	fmt.Scanf("%d", &size1)

	poly1 := make([]int, size1)
	for i := 0; i < size1; i++ {
		poly1[i] = i
	}

	var size2 int
	fmt.Print("Size of the second polynomial: ")
	fmt.Scanf("%d", &size2)

	poly2 := make([]int, size2)
	for i := 0; i < size2; i++ {
		poly2[i] = i
	}

	var numThreads int
	fmt.Print("Number of threads: ")
	fmt.Scanf("%d", &numThreads)

	chunkSize := size1 / numThreads
	remainder := size1 % numThreads

	var wg sync.WaitGroup
	var mu sync.Mutex

	result := make([]int, size1+size2)
	startIdx := 0

	for i := 0; i < numThreads; i++ {
		wg.Add(1)

		currentChunkSize := chunkSize
		if i < remainder {
			currentChunkSize++
		}

		go compute(startIdx, currentChunkSize, poly1, poly2, result, &mu, &wg)

		startIdx += currentChunkSize
	}

	wg.Wait()
	fmt.Print("Done")
}
