package codes.everybody;

/** Thrown when the notes file for a quest part is not on disk yet. */
public final class MissingInputException extends RuntimeException {
    public MissingInputException(String message) {
        super(message);
    }
}
