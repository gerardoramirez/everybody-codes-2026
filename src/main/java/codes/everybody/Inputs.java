package codes.everybody;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Reads quest notes from {@code inputs/questNN/}. */
public final class Inputs {
    private Inputs() {}

    public static Path questDir(int quest) {
        return Path.of("inputs", "quest%02d".formatted(quest));
    }

    public static Path examplePath(int quest, int part) {
        return questDir(quest).resolve("example%d.txt".formatted(part));
    }

    public static Path personalPath(int quest, int part) {
        return questDir(quest).resolve("part%d.txt".formatted(part));
    }

    /**
     * Example files are {@code example1.txt}, {@code example2.txt}, and {@code example3.txt}.
     * If the part-specific file is missing and {@code example.txt} exists, that shared file is used.
     * Personal notes are {@code part1.txt}, {@code part2.txt}, and {@code part3.txt}.
     */
    public static Path resolve(int quest, int part, boolean example) {
        if (!example) {
            return personalPath(quest, part);
        }
        Path specific = examplePath(quest, part);
        if (Files.isRegularFile(specific)) {
            return specific;
        }
        Path shared = questDir(quest).resolve("example.txt");
        if (Files.isRegularFile(shared)) {
            return shared;
        }
        return specific;
    }

    public static String load(int quest, int part, boolean example) {
        Path path = resolve(quest, part, example);
        if (!Files.isRegularFile(path)) {
            throw missing(path, quest, part, example);
        }
        return read(path);
    }

    public static String read(String relativePath) {
        return read(Path.of(relativePath));
    }

    public static String read(Path path) {
        if (!Files.isRegularFile(path)) {
            throw new MissingInputException("""
                    Missing %s
                    Paste the notes into this file.
                    """.formatted(path));
        }
        try {
            String text = Files.readString(path);
            if (text.startsWith("\uFEFF")) {
                text = text.substring(1);
            }
            return text.replace("\r\n", "\n").replace('\r', '\n').stripTrailing();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static MissingInputException missing(Path path, int quest, int part, boolean example) {
        if (example) {
            return new MissingInputException("""
                    Missing %s
                    Paste the Part %d example from %s into that file.
                    """.formatted(path, part, Quests.url(quest)));
        }
        return new MissingInputException("""
                Missing %s
                Log in, open %s, and paste your Part %d notes into that file.
                This file is gitignored.
                """.formatted(path, Quests.url(quest), part));
    }
}
