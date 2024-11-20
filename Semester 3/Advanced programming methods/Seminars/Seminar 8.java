// P1)

Stringstream s;
words.stream().forEach(word -> System.out.println("  " + word));

// f3(f2(f1(x))) -> functional way
// for (word in words) ... imperative way


// P3)

excitingWords = words.stream().map(s->s + "!").collect(collectors.toList())

// P5)

newWords = words.stream().map(String::toUpperCase).filter(s->s.length() < 4).filter(s->s.contains("E")).findFirst().orElse("None");

// P6)
words.stream().reduce(" ", (partialResult, current) -> partialResult + current.toUpperCase());

// P7)
words.stream().map(String::toUpperCase).reduce(" ", (r, s) -> r + s);

// P8)
words.stream().reduce((a, b) -> a + "," + b).orElse("");

// P9)
words.stream().map(words->words.length()).reduce(0, (a, b) -> a + b);

// P10)
words.stream().filter(s->s.contains("h")).count()

// check out the link at the end of seminar 7
