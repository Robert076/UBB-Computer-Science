List<Integer> numbers = Array.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// P1)
numbers.stream().filter(num -> num % 2 == 0 || num % 3 == 0).map(n -> "A" + (n + 1).toString() + "B").reduce(" ", (acc, current) -> acc + current);

// P2)
numbers.stream().filter(s->(s % 4 == 0)).reduce(0, (acc, item) -> acc + (item + 1) % 2)
