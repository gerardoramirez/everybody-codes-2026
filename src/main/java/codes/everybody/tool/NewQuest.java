package codes.everybody.tool;

import codes.everybody.Quests;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/** Creates the class, test, and example file for one quest. */
public final class NewQuest {
    private NewQuest() {}

    public static void main(String[] args) throws IOException {
        if (args.length == 0 || args[0].isBlank()) {
            System.out.println("Usage: ./gradlew newQuest -Pquest=<1-" + Quests.LAST + ">");
            System.exit(2);
            return;
        }
        int quest;
        try {
            quest = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Quest must be a number, got: " + args[0]);
            System.exit(2);
            return;
        }
        int status = scaffold(Path.of(".").toAbsolutePath().normalize(), quest);
        if (status != 0) {
            System.exit(status);
        }
    }

    public static int scaffold(Path root, int quest) throws IOException {
        if (quest < 1 || quest > Quests.LAST) {
            System.out.println("Quest must be 1.." + Quests.LAST + ", got " + quest);
            return 2;
        }

        String packageDir = "quest%02d".formatted(quest);
        String className = "Quest%02d".formatted(quest);
        Path source = root.resolve("src/main/java/codes/everybody/" + packageDir + "/" + className + ".java");
        Path test = root.resolve("src/test/java/codes/everybody/" + packageDir + "/" + className + "Test.java");
        Path example = root.resolve("inputs/" + packageDir + "/example1.txt");

        var created = new ArrayList<Path>();
        if (writeIfAbsent(source, questSource(quest))) {
            created.add(source);
        }
        if (writeIfAbsent(test, testSource(quest))) {
            created.add(test);
        }
        if (writeIfAbsent(example, "\n")) {
            created.add(example);
        }

        if (created.isEmpty()) {
            System.out.println("Quest " + quest + " already exists.");
            return 0;
        }
        System.out.println("Created quest " + quest);
        for (Path path : created) {
            System.out.println("  " + display(root, path));
        }
        return 0;
    }

    private static boolean writeIfAbsent(Path path, String content) throws IOException {
        if (Files.exists(path)) {
            return false;
        }
        Files.createDirectories(path.getParent());
        Files.writeString(path, content);
        return true;
    }

    private static String display(Path root, Path path) {
        return root.relativize(path).toString().replace('\\', '/');
    }

    private static String questSource(int quest) {
        return """
                package codes.everybody.quest%1$02d;

                import codes.everybody.Quest;

                /**
                 * https://everybody.codes/event/2026/quests/%1$d
                 */
                public class Quest%1$02d extends Quest {
                    @Override
                    public Object part1(String input) {
                        // lines(input)      non-blank lines, trimmed
                        // rawLines(input)   every line, blank lines kept
                        // blocks(input)     chunks split by a blank line
                        // tokens(input)     comma or whitespace separated
                        // numbers(input)    every integer in the text
                        // grid(input)       char[row][col]
                        throw notImplemented(1);
                    }
                }
                """.formatted(quest);
    }

    private static String testSource(int quest) {
        return """
                package codes.everybody.quest%1$02d;

                import static org.junit.jupiter.api.Assertions.assertEquals;

                import codes.everybody.Inputs;
                import org.junit.jupiter.api.Disabled;
                import org.junit.jupiter.api.Test;

                class Quest%1$02dTest {
                    private final Quest%1$02d quest = new Quest%1$02d();

                    @Disabled("Paste the example into inputs/quest%1$02d/example1.txt and set the expected answer.")
                    @Test
                    void part1_example() {
                        assertEquals(0L, quest.part1(Inputs.read("inputs/quest%1$02d/example1.txt")));
                    }

                    @Disabled("Part 2 has its own notes: inputs/quest%1$02d/example2.txt")
                    @Test
                    void part2_example() {
                        assertEquals(0L, quest.part2(Inputs.read("inputs/quest%1$02d/example2.txt")));
                    }

                    @Disabled("Part 3 has its own notes: inputs/quest%1$02d/example3.txt")
                    @Test
                    void part3_example() {
                        assertEquals(0L, quest.part3(Inputs.read("inputs/quest%1$02d/example3.txt")));
                    }
                }
                """.formatted(quest);
    }
}
