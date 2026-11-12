# Everybody Codes 2026

Java project for the [Lord of the Wings](https://everybody.codes/event/2026/quests) quests. Each quest has three parts, and each part has its own notes. The published example checks in with the code. Your personal notes stay out of git.

Requires Java 21 or newer. The code targets Java 21, and this machine's Java 25 runs it. The first `./solve` or `./gradlew` command downloads Gradle.

## Solve a quest

1. Log in on the quest page and copy your notes into `inputs/quest01/part1.txt`. Create the file. It is gitignored.
2. Run it:

```bash
./solve 1
```

3. Submit the printed answer on the site. Part 2 opens after Part 1 is accepted, with a new set of notes. Paste those into `inputs/quest01/part2.txt` and implement `part2`. Part 3 works the same way.

Check a solution against the published example before using your notes:

```bash
./solve 1 example
./solve 1 2 example
./gradlew test
```

`./solve 1` runs every part that has notes. A missing part is skipped. Naming a part that is missing or not written yet fails the command, so a wrong file does not look like a wrong answer.

## Start another quest

```bash
./gradlew newQuest -Pquest=2
```

That creates:

- `src/main/java/codes/everybody/quest02/Quest02.java`
- `src/test/java/codes/everybody/quest02/Quest02Test.java`
- `inputs/quest02/example1.txt`

Paste the example from the site into `example1.txt`, put the expected answer in the test, and remove `@Disabled`. Personal notes go in `inputs/quest02/part1.txt`, `part2.txt`, and `part3.txt`. A shared `example.txt` is used only when the part-specific example file is absent.

## Where the code goes

`Quest` holds the parsing helpers: `lines`, `rawLines`, `blocks`, `tokens`, `numbers`, and `grid`. A solution returns a `long`, a `BigInteger`, or a `String`. Tests compare with `equals`, so `assertEquals(11L, quest.part1(input))` matches a returned `long`.

Quest 1 part 1 is already solved from the published example, so `./gradlew test` passes on a fresh checkout. Clear `Quest01.part1` if you want to solve that part yourself. Parts 2 and 3 are empty.

Running `./solve` with no arguments prints the full usage. `./gradlew run --args="1 example"` is the same command without the wrapper script.
