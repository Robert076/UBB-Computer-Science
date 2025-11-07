package main

import (
	"fmt"
	"sync"
)

func ComputeSingleElement(row []int, col []int) int {
	sum := 0
	for i := range row {
		sum += row[i] * col[i]
	}
	return sum
}

func ComputeProductOfMatrices(matrix1 [][]int, matrix2 [][]int) [][]int {

	n := len(matrix1)
	m := len(matrix2[0])

	result := make([][]int, n)
	for i := range result {
		result[i] = make([]int, m)
	}

	threads := 4
	var wg sync.WaitGroup
	wg.Add(threads)

	totalElements := n * m
	block := totalElements / threads

	for t := 0; t < threads; t++ {
		go func(id int) {
			defer wg.Done()

			start := id * block
			end := start + block
			if id == threads-1 {
				end = totalElements
			}

			for idx := start; idx < end; idx++ {
				r := idx / m
				c := idx % m
				// c := idx / m      for p2
				// r := idx % m

				col := make([]int, len(matrix2))
				for i := 0; i < len(matrix2); i++ {
					col[i] = matrix2[i][c]
				}

				result[r][c] = ComputeSingleElement(matrix1[r], col)
			}

		}(t)
	}

	wg.Wait()
	return result
}

func main() {

	A := [][]int{
		{1, 2, 3, 4, 5, 6, 7, 8, 9},
		{2, 3, 4, 5, 6, 7, 8, 9, 1},
		{3, 4, 5, 6, 7, 8, 9, 1, 2},
		{4, 5, 6, 7, 8, 9, 1, 2, 3},
		{5, 6, 7, 8, 9, 1, 2, 3, 4},
		{6, 7, 8, 9, 1, 2, 3, 4, 5},
		{7, 8, 9, 1, 2, 3, 4, 5, 6},
		{8, 9, 1, 2, 3, 4, 5, 6, 7},
		{9, 1, 2, 3, 4, 5, 6, 7, 8},
	}

	B := [][]int{
		{9, 8, 7, 6, 5, 4, 3, 2, 1},
		{8, 7, 6, 5, 4, 3, 2, 1, 9},
		{7, 6, 5, 4, 3, 2, 1, 9, 8},
		{6, 5, 4, 3, 2, 1, 9, 8, 7},
		{5, 4, 3, 2, 1, 9, 8, 7, 6},
		{4, 3, 2, 1, 9, 8, 7, 6, 5},
		{3, 2, 1, 9, 8, 7, 6, 5, 4},
		{2, 1, 9, 8, 7, 6, 5, 4, 3},
		{1, 9, 8, 7, 6, 5, 4, 3, 2},
	}

	C := ComputeProductOfMatrices(A, B)

	fmt.Println("Rezultat:")
	for i := range C {
		fmt.Println(C[i])
	}
}
