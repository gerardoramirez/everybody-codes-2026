package codes.everybody;

import java.util.List;
import java.util.Locale;

/** Command line entry point. Prefer {@code ./solve} over calling this class directly. */
public final class Runner {
    private Runner() {}

    public static void main(String[] args) {
        try {
            int status = run(args);
            if (status != 0) {
                System.exit(status);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println();
            usage();
            System.exit(2);
        }
    }

    static int run(String[] args) {
        if (args.length == 0 || args[0].equals("help") || args[0].equals("--help")) {
            usage();
            return 0;
        }

        int number = parseQuest(args[0]);
        Integer onlyPart = null;
        boolean example = false;
        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("example")) {
                example = true;
                continue;
            }
            if (onlyPart != null || !arg.chars().allMatch(Character::isDigit)) {
                throw new IllegalArgumentException("Unknown argument: " + arg);
            }
            int part = Integer.parseInt(arg);
            if (part < 1 || part > 3) {
                throw new IllegalArgumentException("Part must be 1, 2, or 3");
            }
            onlyPart = part;
        }

        Quest quest = Quests.get(number);
        System.out.println(quest.name());
        System.out.println(Quests.url(number));
        System.out.println(example ? "Input: example" : "Input: your notes");

        int ran = 0;
        int failed = 0;
        List<Integer> parts = onlyPart == null ? List.of(1, 2, 3) : List.of(onlyPart);
        for (int part : parts) {
            String input;
            try {
                input = Inputs.load(number, part, example);
            } catch (MissingInputException e) {
                boolean neededNow = onlyPart != null || part == 1;
                if (neededNow) {
                    System.out.println(e.getMessage().stripTrailing());
                    failed++;
                } else {
                    System.out.printf(
                            "  part %d: skipped, missing %s%n",
                            part, Inputs.resolve(number, part, example));
                }
                continue;
            }

            long started = System.nanoTime();
            try {
                Object answer = invoke(quest, part, input);
                double millis = (System.nanoTime() - started) / 1_000_000.0;
                if (answer == null) {
                    System.out.printf("  part %d: null%n", part);
                    failed++;
                    continue;
                }
                System.out.printf(Locale.ROOT, "  part %d: %s  (%.2f ms)%n", part, answer, millis);
                ran++;
            } catch (NotImplementedException e) {
                System.out.printf("  part %d: not implemented%n", part);
                if (onlyPart != null) {
                    failed++;
                }
            } catch (Exception e) {
                System.out.printf("  part %d failed:%n", part);
                e.printStackTrace(System.out);
                failed++;
            }
        }
        return failed > 0 || ran == 0 ? 1 : 0;
    }

    private static Object invoke(Quest quest, int part, String input) {
        return switch (part) {
            case 1 -> quest.part1(input);
            case 2 -> quest.part2(input);
            case 3 -> quest.part3(input);
            default -> throw new IllegalArgumentException("Part must be 1, 2, or 3");
        };
    }

    private static int parseQuest(String text) {
        if (text.isEmpty() || !text.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException("Quest must be a number, got: " + text);
        }
        int number = Integer.parseInt(text);
        if (number < 1 || number > Quests.LAST) {
            throw new IllegalArgumentException("Quest must be 1.." + Quests.LAST + ", got " + number);
        }
        return number;
    }

    private static void usage() {
        System.out.println("""
                Run an Everybody Codes quest.

                  ./solve <quest>
                  ./solve <quest> <part>
                  ./solve <quest> example
                  ./solve <quest> <part> example

                <quest> is 1..20. <part> is 1, 2, or 3. Without a part, every part runs.
                "example" reads inputs/questNN/exampleN.txt. Otherwise the runner reads
                inputs/questNN/partN.txt, which is gitignored.

                Start a new quest with:
                  ./gradlew newQuest -Pquest=2
                """);
    }
}
