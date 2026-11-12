package codes.everybody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * One Everybody Codes quest. Put the class in {@code codes.everybody.questNN.QuestNN}.
 *
 * <p>Each quest has three parts, and the site gives every part its own notes. Return a
 * {@code long}, a {@code BigInteger}, or a {@code String}. The runner prints that value.
 */
public abstract class Quest {
    private static final Pattern NUMBER = Pattern.compile("-?\\d+");

    public abstract Object part1(String input);

    public Object part2(String input) {
        throw notImplemented(2);
    }

    public Object part3(String input) {
        throw notImplemented(3);
    }

    public int number() {
        String digits = getClass().getSimpleName().replaceAll("\\D+", "");
        if (digits.isEmpty()) {
            throw new IllegalStateException(getClass().getName() + " has no quest number");
        }
        return Integer.parseInt(digits);
    }

    public String name() {
        return "Quest " + number();
    }

    protected NotImplementedException notImplemented(int part) {
        return new NotImplementedException(name() + " part " + part + " is not implemented");
    }

    /** Non-blank lines with surrounding whitespace removed. */
    protected static List<String> lines(String input) {
        return input.lines().map(String::strip).filter(line -> !line.isEmpty()).toList();
    }

    /** Every line, including blank lines. Trailing whitespace on the whole input is dropped. */
    protected static List<String> rawLines(String input) {
        if (input.isBlank()) {
            return List.of();
        }
        return input.stripTrailing().lines().toList();
    }

    /** Chunks separated by a blank line. */
    protected static List<String> blocks(String input) {
        if (input.isBlank()) {
            return List.of();
        }
        return Arrays.stream(input.strip().split("\\R\\s*\\R"))
                .map(String::strip)
                .filter(block -> !block.isEmpty())
                .toList();
    }

    /** Tokens split on commas or whitespace. */
    protected static List<String> tokens(String input) {
        if (input.isBlank()) {
            return List.of();
        }
        return Arrays.stream(input.trim().split("[,\\s]+"))
                .filter(token -> !token.isEmpty())
                .toList();
    }

    /** Every integer in the text, in order, including negatives. */
    protected static long[] numbers(String input) {
        Matcher matcher = NUMBER.matcher(input);
        var values = new ArrayList<Long>();
        while (matcher.find()) {
            values.add(Long.parseLong(matcher.group()));
        }
        return values.stream().mapToLong(Long::longValue).toArray();
    }

    /** Rows of characters. Line {@code r} is {@code grid[r]}, column {@code c} is {@code grid[r][c]}. */
    protected static char[][] grid(String input) {
        List<String> rows = rawLines(input);
        char[][] cells = new char[rows.size()][];
        for (int row = 0; row < rows.size(); row++) {
            cells[row] = rows.get(row).toCharArray();
        }
        return cells;
    }
}
