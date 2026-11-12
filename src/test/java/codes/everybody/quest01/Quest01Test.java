package codes.everybody.quest01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import codes.everybody.Inputs;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Quest01Test {
    private final Quest01 quest = new Quest01();

    @Test
    void part1_singleOutpost() {
        assertEquals(6L, quest.part1("10"));
        assertEquals(0L, quest.part1("1"));
        assertEquals(0L, quest.part1("16"));
    }

    @Test
    void part1_example() {
        assertEquals(11L, quest.part1(Inputs.read("inputs/quest01/example1.txt")));
    }

    @Disabled("Part 2 unlocks after Part 1. Paste inputs/quest01/example2.txt and set the expected answer.")
    @Test
    void part2_example() {
        assertEquals(0L, quest.part2(Inputs.read("inputs/quest01/example2.txt")));
    }

    @Disabled("Part 3 has its own notes: inputs/quest01/example3.txt")
    @Test
    void part3_example() {
        assertEquals(0L, quest.part3(Inputs.read("inputs/quest01/example3.txt")));
    }
}
