package codes.everybody;

/** Finds the class for a quest number. Classes live in {@code questNN.QuestNN}. */
public final class Quests {
    public static final int LAST = 20;

    private Quests() {}

    public static String url(int number) {
        return "https://everybody.codes/event/2026/quests/" + number;
    }

    public static Quest get(int number) {
        if (number < 1 || number > LAST) {
            throw new IllegalArgumentException("Quest must be 1.." + LAST + ", got " + number);
        }
        String className = "codes.everybody.quest%02d.Quest%02d".formatted(number, number);
        try {
            Class<?> type = Class.forName(className);
            return (Quest) type.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(
                    "No class " + className + ". Create it with: ./gradlew newQuest -Pquest=" + number, e);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Could not create " + className, e);
        }
    }
}
