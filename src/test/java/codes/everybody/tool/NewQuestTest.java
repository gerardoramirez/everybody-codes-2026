package codes.everybody.tool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class NewQuestTest {
    @Test
    void scaffoldsAQuestOnce(@TempDir Path root) throws IOException {
        assertEquals(0, NewQuest.scaffold(root, 4));

        Path source = root.resolve("src/main/java/codes/everybody/quest04/Quest04.java");
        String text = Files.readString(source);
        assertTrue(text.startsWith("package codes.everybody.quest04;\n"));
        assertTrue(text.contains("public class Quest04 extends Quest {\n"));
        assertTrue(text.contains("    @Override\n"));
        assertTrue(text.contains("        throw notImplemented(1);\n"));
        assertTrue(text.contains("https://everybody.codes/event/2026/quests/4"));

        Path test = root.resolve("src/test/java/codes/everybody/quest04/Quest04Test.java");
        String testText = Files.readString(test);
        assertTrue(testText.contains("@Disabled"));
        assertTrue(testText.contains("inputs/quest04/example1.txt"));
        assertTrue(Files.isRegularFile(root.resolve("inputs/quest04/example1.txt")));

        assertEquals(0, NewQuest.scaffold(root, 4));
        assertEquals(text, Files.readString(source));
    }

    @Test
    void rejectsAQuestNumberOutsideTheEvent(@TempDir Path root) throws IOException {
        assertEquals(2, NewQuest.scaffold(root, 0));
        assertEquals(2, NewQuest.scaffold(root, 21));
        assertFalse(Files.exists(root.resolve("src")));
    }
}
