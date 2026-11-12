package codes.everybody;

/** Thrown by a quest part that has not been written yet. */
public final class NotImplementedException extends RuntimeException {
    public NotImplementedException(String message) {
        super(message);
    }
}
