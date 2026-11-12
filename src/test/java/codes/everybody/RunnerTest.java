package codes.everybody;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class RunnerTest {
    @Test
    void loadsQuest1() {
        Quest quest = Quests.get(1);
        assertEquals(1, quest.number());
        assertEquals("Quest 1: The Coneservation", quest.name());
    }

    @Test
    void unknownQuestTellsYouHowToScaffold() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> Quests.get(2));
        assertTrue(error.getMessage().contains("newQuest"));
    }

    @Test
    void exampleRunPrintsPart1() {
        String output = capture(() -> assertEquals(0, Runner.run(new String[] {"1", "example"})));
        assertTrue(output.contains("part 1: 11"));
        assertTrue(output.contains("part 2: skipped"));
        assertTrue(output.contains("part 3: skipped"));
    }

    @Test
    void rejectsUnknownArguments() {
        assertThrows(IllegalArgumentException.class, () -> Runner.run(new String[] {"1", "nope"}));
    }

    private static String capture(Runnable action) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            action.run();
            return buffer.toString(StandardCharsets.UTF_8);
        } finally {
            System.setOut(original);
        }
    }
}
