package liubomyr.stepanenko.bookstore.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import liubomyr.stepanenko.bookstore.validation.Isbn;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    private static final String ISBN_PATTERN = "^\\d{3}-\\d{10}$";

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        return isbn != null && Pattern.compile(ISBN_PATTERN).matcher(isbn).matches();
    }
}
