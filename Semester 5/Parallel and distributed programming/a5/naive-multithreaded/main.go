package main

import (
	"fmt"
	"sync"
	"time"
)

func multiplyPolynomials(poly1 []int, poly2 []int, result *[]int, mu *sync.Mutex, offset int) {
	for i := 0; i < len(poly1); i++ {
		for j := 0; j < len(poly2); j++ {
			(*result)[i+j+offset] += poly1[i] * poly2[j]
		}
	}
}

func readPolynomial() []int {
	fmt.Print("\nPlease enter the degree of the polynomial: ")
	var order int
	fmt.Scanln(&order)

	fmt.Println("\nPlease enter the coefficients, one by one")
	coefficients := make([]int, order+1)
	for i := 0; i <= order; i++ {
		fmt.Printf("x^%d: ", i)
		fmt.Scanln(&coefficients[i])
	}

	return coefficients
}

func main() {
	fmt.Println("Naive multiplication without multithreading")
	fmt.Println("Please pick one of the following:")
	fmt.Println(" 1. Test on custom input")
	fmt.Println(" 2. Test on a huge, prebuilt input")
	var userChoice int
	fmt.Scanln(&userChoice)
	switch userChoice {
	case 1:
		testNormalInput()
	case 2:
		testLargeInput()
	default:
		fmt.Println("Unexpected input. Exiting...")
	}
}

func testNormalInput() {
	fmt.Print("Welcome to the naive, multithreaded implementation\n\n")
	fmt.Printf("==========================\n\n")
	fmt.Printf("STEP 0: Reading the number of threads\n")
	fmt.Print("The number of threads will be: ")
	var numThreads int
	fmt.Scanln(&numThreads)
	fmt.Printf("==========================\n\n")
	fmt.Printf("STEP 2: Reading the first polynomial\n")
	poly1 := readPolynomial()
	fmt.Printf("\n==========================\n\n")
	fmt.Print("STEP 3: Reading the second polynomial\n")
	poly2 := readPolynomial()
	fmt.Println("\n==========================")

	var wg sync.WaitGroup
	wg.Add(numThreads)
	var mu sync.Mutex
	result := make([]int, len(poly1)+len(poly2)-1)
	for i := 0; i < numThreads; i++ {
		curr := i
		go func() {
			defer wg.Done()
			blockSize := (len(poly1) + numThreads - 1) / numThreads
			start := curr * blockSize
			end := (curr + 1) * blockSize
			if end > len(poly1) {
				end = len(poly1)
			}
			multiplyPolynomials(poly1[start:end], poly2, &result, &mu, start)
		}()
	}
	wg.Wait()
	fmt.Printf("\n==========================\n\n")
	fmt.Print("RESULT: ")
	fmt.Print(result)
}

func testLargeInput() {
	fmt.Printf("==========================\n\n")
	fmt.Printf("STEP 0: Reading the number of threads\n")
	fmt.Print("The number of threads will be: ")
	var numThreads int
	fmt.Scanln(&numThreads)
	fmt.Printf("==========================\n\n")
	LIM := 10000
	poly1 := make([]int, LIM)
	poly2 := make([]int, LIM)
	for i := 0; i < LIM; i++ {
		poly1[i] = 1
		poly2[i] = 1
	}

	tStart := time.Now()
	var wg sync.WaitGroup
	wg.Add(numThreads)
	var mu sync.Mutex
	result := make([]int, len(poly1)+len(poly2)-1)
	for i := 0; i < numThreads; i++ {
		curr := i
		go func() {
			defer wg.Done()
			blockSize := (len(poly1) + numThreads - 1) / numThreads
			start := curr * blockSize
			end := (curr + 1) * blockSize
			if end > len(poly1) {
				end = len(poly1)
			}
			multiplyPolynomials(poly1[start:end], poly2, &result, &mu, start)
		}()
	}
	wg.Wait()
	tEnd := time.Now()
	fmt.Printf("Duration: %.6f seconds.", tEnd.Sub(tStart).Seconds())
}
