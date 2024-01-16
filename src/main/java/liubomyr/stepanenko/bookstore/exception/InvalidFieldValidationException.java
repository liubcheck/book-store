package liubomyr.stepanenko.bookstore.exception;

public class InvalidFieldValidationException extends RuntimeException {
    public InvalidFieldValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
