package main

import (
	"fmt"
	"sync"
	"time"
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

func karatsubaMT(poly1, poly2 []int) []int {
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

	var z0, z1, z2 []int
	var wg sync.WaitGroup

	if len(low1) < 10000 || len(low2) < 10000 {
		z0 = karatsubaMT(low1, low2)
	} else {
		wg.Add(1)
		go func() {
			defer wg.Done()
			z0 = karatsubaMT(low1, low2)
		}()
	}

	if len(high1) < 10000 || len(high2) < 10000 {
		z2 = karatsubaMT(high1, high2)
	} else {
		wg.Add(1)
		go func() {
			defer wg.Done()
			z2 = karatsubaMT(high1, high2)
		}()
	}

	if len(low1) < 10000 || len(high1) < 10000 {
		z1 = karatsubaMT(addPolynomials(low1, high1), addPolynomials(low2, high2))
	} else {
		wg.Add(1)
		go func() {
			defer wg.Done()
			z1 = karatsubaMT(addPolynomials(low1, high1), addPolynomials(low2, high2))
		}()
	}

	wg.Wait()
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

// A(x) * B(x) = (A_low + x^m*A_high) * (B_low + x^m*B_high) = A_low*B_low + A_low*x^m*B_high + B_low*x^m*A_high + x^2m*A_high*B_high

func main() {
	fmt.Println("Karatsuba multiplication without multithreading")
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

func testLargeInput() {
	LIM := 100000
	poly1 := make([]int, LIM)
	for i := 0; i < LIM; i++ {
		poly1[i] = 1
	}
	poly2 := make([]int, LIM)
	for i := 0; i < LIM; i++ {
		poly2[i] = 1
	}
	tStart := time.Now()
	karatsubaMT(poly1, poly2)
	tEnd := time.Now()
	fmt.Printf("Duration: %.6f seconds.", tEnd.Sub(tStart).Seconds())
}

func testNormalInput() {
	fmt.Printf("==========================\n")
	fmt.Println("STEP 1: Reading the first polynomial")
	poly1 := readPolynomial()
	fmt.Printf("\nSTEP 2: Reading the second polynomial\n")
	poly2 := readPolynomial()
	fmt.Printf("\n==========================\n")
	tStart := time.Now()
	karatsubaMT(poly1, poly2)
	tEnd := time.Now()
	fmt.Printf("Duration: %.6f seconds.\n", tEnd.Sub(tStart).Seconds())
}
