package codes.everybody;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class InputsTest {
    @Test
    void personalNotesLiveNextToExamples() {
        assertEquals(Path.of("inputs/quest01/part1.txt"), Inputs.personalPath(1, 1));
        assertEquals(Path.of("inputs/quest01/example2.txt"), Inputs.examplePath(1, 2));
    }

    @Test
    void readNormalizesPaste(@TempDir Path dir) throws IOException {
        Path notes = dir.resolve("notes.txt");
        Files.writeString(notes, "\uFEFF3, 4\r\n");
        assertEquals("3, 4", Inputs.read(notes));
    }

    @Test
    void missingFileNamesThePath() {
        Path path = Path.of("inputs/quest01/does-not-exist.txt");
        MissingInputException error = assertThrows(MissingInputException.class, () -> Inputs.read(path));
        assertTrue(error.getMessage().contains(path.toString()));
    }
}
