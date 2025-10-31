package main

import (
	"fmt"
	"sync"
)

type Queue struct {
	items   []int
	maxSize int
}

func (q *Queue) Push(item int) {
	q.items = append(q.items, item)
}

func (d *Queue) Pop() int {
	if len(d.items) == 0 {
		return 0
	}

	frontElement := d.items[0]
	d.items = d.items[1:]
	return frontElement
}

func Producer(q *Queue, x, y []int, mu *sync.Mutex, notEmpty, notFull *sync.Cond, wg *sync.WaitGroup) {
	defer wg.Done()
	for i := range x {
		mu.Lock()
		for len(q.items) == q.maxSize {
			notFull.Wait()
		}
		q.Push(x[i] * y[i])
		notEmpty.Signal()
		mu.Unlock()
	}
}

func Consumer(q *Queue, mu *sync.Mutex, notEmpty, notFull *sync.Cond, sum *int, total int, wg *sync.WaitGroup) {
	defer wg.Done()
	for i := 0; i < total; i++ {
		mu.Lock()
		for len(q.items) == 0 {
			notEmpty.Wait()
		}
		val := q.Pop()
		*sum += val
		notFull.Signal()
		mu.Unlock()
	}
}

func main() {
	x := []int{1, 8, 3, 9, 12, 3}
	y := []int{3, 11, 2, 8, 8, 1}

	q := &Queue{maxSize: 3}
	mu := &sync.Mutex{}
	notEmpty := sync.NewCond(mu)
	notFull := sync.NewCond(mu)
	wg := sync.WaitGroup{}
	var sum int

	wg.Add(2)
	go Producer(q, x, y, mu, notEmpty, notFull, &wg)
	go Consumer(q, mu, notEmpty, notFull, &sum, len(x), &wg)
	wg.Wait()

	fmt.Println("Scalar product:", sum)
}
