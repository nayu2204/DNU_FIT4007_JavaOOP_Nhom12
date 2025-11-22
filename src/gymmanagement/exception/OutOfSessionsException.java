package gymmanagement.exception;

public class OutOfSessionsException extends Exception {
    public OutOfSessionsException(String message) {
        super(message);
    }
}