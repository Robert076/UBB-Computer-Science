package main

import (
	"fmt"
	"log"
	"sync"
)

func compute(startRow, endRow, size int, arr1, arr2, resultMatrix [][]int, wg *sync.WaitGroup) {
	defer wg.Done()

	for i := startRow; i < endRow; i++ {
		for j := 0; j < size; j++ {
			sum := 0
			for k := 0; k < size; k++ {
				sum += arr1[i][k] * arr2[k][j]
			}
			resultMatrix[i][j] = sum
		}
	}
}

func main() {
	var size int
	fmt.Print("Value of 'n': ")
	_, err := fmt.Scanf("%d", &size)
	if err != nil {
		log.Fatal(err)
	}

	arr1 := make([][]int, size)
	for i := 0; i < size; i++ {
		arr1[i] = make([]int, size)
		for j := 0; j < size; j++ {
			arr1[i][j] = j
		}
	}

	arr2 := make([][]int, size)
	for i := 0; i < size; i++ {
		arr2[i] = make([]int, size)
		for j := 0; j < size; j++ {
			arr2[i][j] = j
		}
	}

	resultMatrix := make([][]int, size)
	for i := 0; i < size; i++ {
		resultMatrix[i] = make([]int, size)
	}

	var wg sync.WaitGroup

	var numThreads int
	fmt.Print("Number of threads: ")
	fmt.Scanf("%d", &numThreads)

	rowsPerThread := size / numThreads
	remainder := size % numThreads

	startRow := 0
	for i := 0; i < numThreads; i++ {
		extra := 0
		if i < remainder {
			extra = 1
		}

		endRow := startRow + rowsPerThread + extra
		wg.Add(1)

		go compute(startRow, endRow, size, arr1, arr2, resultMatrix, &wg)

		startRow = endRow
	}

	wg.Wait()
	fmt.Print("Done")
}
