package codes.everybody.quest01;

import codes.everybody.Quest;

/**
 * Quest 1: The Coneservation.
 *
 * <p>Part 1 is filled in so the template has a working example. Delete the method body if you
 * want to solve it yourself. Parts 2 and 3 are open.
 *
 * <p>https://everybody.codes/event/2026/quests/1
 */
public class Quest01 extends Quest {
    @Override
    public String name() {
        return "Quest 1: The Coneservation";
    }

    @Override
    public Object part1(String input) {
        long collected = 0;
        for (long cones : numbers(input)) {
            collected += conesToNextSquare(cones);
        }
        return collected;
    }

    /** Cones to add so {@code have} fills a square, or zero when it already does. */
    private static long conesToNextSquare(long have) {
        if (have < 1) {
            throw new IllegalArgumentException("cone count must be positive, got " + have);
        }
        long side = (long) Math.sqrt(have);
        if (side < 1) {
            side = 1;
        }
        // Math.sqrt is not exact for every long, so walk onto the real integer square.
        while (square(side) < have) {
            side++;
        }
        while (side > 1 && square(side - 1) >= have) {
            side--;
        }
        return square(side) - have;
    }

    private static long square(long side) {
        return Math.multiplyExact(side, side);
    }
}
