package codes.everybody;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class QuestHelpersTest extends Quest {
    @Override
    public Object part1(String input) {
        return null;
    }

    @Test
    void linesDropBlanksAndTrim() {
        assertEquals(List.of("A", "B"), lines(" A \n\n B \n"));
    }

    @Test
    void rawLinesKeepBlankLines() {
        assertEquals(List.of("A", "", "B"), rawLines("A\n\nB\n"));
    }

    @Test
    void blocksSplitOnBlankLines() {
        assertEquals(List.of("a\nb", "c"), blocks("a\nb\n\nc\n"));
    }

    @Test
    void tokensSplitOnCommasAndWhitespace() {
        assertEquals(List.of("3", "11", "12"), tokens("3,11, 12"));
    }

    @Test
    void numbersFindIntegersInAnyText() {
        assertArrayEquals(new long[] {7, -3, 12}, numbers("id=7 time=-3\nextra 12"));
    }

    @Test
    void gridIndexesRowThenColumn() {
        char[][] cells = grid("AB\nCD\n");
        assertEquals('A', cells[0][0]);
        assertEquals('C', cells[1][0]);
        assertEquals('D', cells[1][1]);
    }
}
