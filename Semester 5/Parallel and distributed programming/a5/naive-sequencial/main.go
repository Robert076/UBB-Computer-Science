package main

import (
	"fmt"
)

func multiplyPolynomials(poly1 []int, poly2 []int) []int {
	result := make([]int, len(poly1)+len(poly2)-1)
	for i := 0; i < len(poly1); i++ {
		for j := 0; j < len(poly2); j++ {
			result[i+j] += poly1[i] * poly2[j]
		}
	}

	return result
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

func getUserInput() ([]int, []int) {
	fmt.Print("Welcome to the naive, sequential implementation\n\n")
	fmt.Printf("==========================\n\n")
	fmt.Printf("STEP 1: Reading the first polynomial\n")
	coefficientsFirst := readPolynomial()
	fmt.Printf("\n==========================\n\n")
	fmt.Print("STEP 2: Reading the second polynomial\n")
	coefficientsSecond := readPolynomial()
	fmt.Println("\n==========================")

	return coefficientsFirst, coefficientsSecond
}

func main() {
	poly1, poly2 := getUserInput()
	result := multiplyPolynomials(poly1, poly2)
	fmt.Printf("\n==========================\n\n")
	fmt.Print("RESULT: ")
	fmt.Print(result)
}
