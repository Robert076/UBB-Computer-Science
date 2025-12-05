package main

import (
	"fmt"
)

func addPolynomials(a, b []int) []int {
	n := max(len(a), len(b))
	res := make([]int, n)
	for i := 0; i < n; i++ {
		if i < len(a) {
			res[i] += a[i]
		}
		if i < len(b) {
			res[i] += b[i]
		}
	}
	return res
}

func subPolynomials(a, b []int) []int {
	n := max(len(a), len(b))
	res := make([]int, n)
	for i := 0; i < n; i++ {
		if i < len(a) {
			res[i] += a[i]
		}
		if i < len(b) {
			res[i] -= b[i]
		}
	}
	return res
}

func karatsuba(poly1, poly2 []int) []int {
	n := max(len(poly1), len(poly2))
	if n == 1 {
		return []int{poly1[0] * poly2[0]}
	}

	p1 := make([]int, n)
	p2 := make([]int, n)
	copy(p1, poly1)
	copy(p2, poly2)

	mid := n / 2

	low1, high1 := p1[:mid], p1[mid:]
	low2, high2 := p2[:mid], p2[mid:]

	z0 := karatsuba(low1, low2)
	z2 := karatsuba(high1, high2)
	z1 := karatsuba(addPolynomials(low1, high1), addPolynomials(low2, high2))
	z1 = subPolynomials(subPolynomials(z1, z0), z2)

	res := make([]int, 2*n)
	for i := 0; i < len(z0); i++ {
		res[i] += z0[i]
	}
	for i := 0; i < len(z1); i++ {
		res[i+mid] += z1[i]
	}
	for i := 0; i < len(z2); i++ {
		res[i+2*mid] += z2[i]
	}
	for len(res) > 1 && res[len(res)-1] == 0 {
		res = res[:len(res)-1]
	}
	return res
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
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
	fmt.Println("Karatsuba multiplication without multithreading")
	fmt.Printf("==========================\n")
	fmt.Println("STEP 1: Reading the first polynomial")
	poly1 := readPolynomial()
	fmt.Printf("\nSTEP 2: Reading the second polynomial\n")
	poly2 := readPolynomial()
	fmt.Printf("\n==========================\n")
	result := karatsuba(poly1, poly2)
	fmt.Println("RESULT:", result)
}
