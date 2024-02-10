package liubomyr.stepanenko.bookstore.exception;

public class EmptyShoppingCartException extends RuntimeException {
    public EmptyShoppingCartException(String message) {
        super(message);
    }
}
