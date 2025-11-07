package main

import (
	"fmt"
	"sync"
	"time"
)

func ComputeSingleElement(row []int, col []int) int {
	sum := 0
	for i := range row {
		sum += row[i] * col[i]
	}
	return sum
}

func ComputeProductOfMatrices(matrix1 [][]int, matrix2 [][]int, th int) [][]int {

	n := len(matrix1)
	m := len(matrix2[0])

	result := make([][]int, n)
	for i := range result {
		result[i] = make([]int, m)
	}

	threads := th
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

	// A := [][]int{
	// 	{1, 2, 3, 4, 5, 6, 7, 8, 9},
	// 	{2, 3, 4, 5, 6, 7, 8, 9, 1},
	// 	{3, 4, 5, 6, 7, 8, 9, 1, 2},
	// 	{4, 5, 6, 7, 8, 9, 1, 2, 3},
	// 	{5, 6, 7, 8, 9, 1, 2, 3, 4},
	// 	{6, 7, 8, 9, 1, 2, 3, 4, 5},
	// 	{7, 8, 9, 1, 2, 3, 4, 5, 6},
	// 	{8, 9, 1, 2, 3, 4, 5, 6, 7},
	// 	{9, 1, 2, 3, 4, 5, 6, 7, 8},
	// }

	// B := [][]int{
	// 	{9, 8, 7, 6, 5, 4, 3, 2, 1},
	// 	{8, 7, 6, 5, 4, 3, 2, 1, 9},
	// 	{7, 6, 5, 4, 3, 2, 1, 9, 8},
	// 	{6, 5, 4, 3, 2, 1, 9, 8, 7},
	// 	{5, 4, 3, 2, 1, 9, 8, 7, 6},
	// 	{4, 3, 2, 1, 9, 8, 7, 6, 5},
	// 	{3, 2, 1, 9, 8, 7, 6, 5, 4},
	// 	{2, 1, 9, 8, 7, 6, 5, 4, 3},
	// 	{1, 9, 8, 7, 6, 5, 4, 3, 2},
	// }

	var userInput int
	fmt.Println("Which program do you want to run?")
	fmt.Println(" 1. Normal program, I will read my matrices.")
	fmt.Println(" 2. Benchmark. Generate the biggest matrices possible.")
	fmt.Print("Your option: \n> ")
	fmt.Scan(&userInput)
	if userInput == 1 {
		Solve1()
	} else {
		Solve2()
	}
}

func Solve1() {
	var n, m int
	var p, q int

	fmt.Print("Introdu dimensiunile matricei A (n m): ")
	fmt.Scan(&n, &m)

	A := make([][]int, n)
	fmt.Println("Introdu matricea A:")
	for i := 0; i < n; i++ {
		A[i] = make([]int, m)
		for j := 0; j < m; j++ {
			fmt.Scan(&A[i][j])
		}
	}

	fmt.Print("Introdu dimensiunile matricei B (p q): ")
	fmt.Scan(&p, &q)

	if m != p {
		fmt.Println("Nu se pot inmulti matricile: m trebuie sa fie egal cu p.")
		return
	}

	B := make([][]int, p)
	fmt.Println("Introdu matricea B:")
	for i := 0; i < p; i++ {
		B[i] = make([]int, q)
		for j := 0; j < q; j++ {
			fmt.Scan(&B[i][j])
		}
	}

	C := ComputeProductOfMatrices(A, B, 4)

	fmt.Println("Rezultat:")
	for i := range C {
		fmt.Println(C[i])
	}
}

func Solve2() {
	var n int
	var m int
	var p int
	var q int
	var th int

	fmt.Println("Rows in first matrix: ")
	fmt.Print("> ")
	fmt.Scan(&n)
	fmt.Println("Columns in first matrix: ")
	fmt.Print("> ")
	fmt.Scan(&m)
	fmt.Println("Rows in second matrix: ")
	fmt.Print("> ")
	fmt.Scan(&p)
	fmt.Println("Columns in second matrix: ")
	fmt.Print("> ")
	fmt.Scan(&q)
	fmt.Println("Number of threads: ")
	fmt.Scan(&th)

	A := make([][]int, n)
	fmt.Println("Creating matrix A...")
	for i := 0; i < n; i++ {
		A[i] = make([]int, m)
		for j := 0; j < m; j++ {
			A[i][j] = i + j
		}
	}
	fmt.Println("Matrix A created successfully")

	B := make([][]int, p)
	fmt.Println("Creating matrix B...")
	for i := 0; i < p; i++ {
		B[i] = make([]int, q)
		for j := 0; j < q; j++ {
			B[i][j] = i + j
		}
	}
	fmt.Println("Matrix B created successfully")

	tStart := time.Now()
	ComputeProductOfMatrices(A, B, th)
	tEnd := time.Now()
	fmt.Printf("Duration: %.6f seconds.", tEnd.Sub(tStart).Seconds())
}
