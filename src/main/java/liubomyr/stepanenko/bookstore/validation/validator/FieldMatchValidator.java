package liubomyr.stepanenko.bookstore.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;
import liubomyr.stepanenko.bookstore.exception.InvalidFieldValidationException;
import liubomyr.stepanenko.bookstore.validation.FieldMatch;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field firstField = value.getClass().getDeclaredField(firstFieldName);
            firstField.setAccessible(true);
            Object firstObj = firstField.get(value);

            Field secondField = value.getClass().getDeclaredField(secondFieldName);
            secondField.setAccessible(true);
            Object secondObj = secondField.get(value);

            return Objects.equals(firstObj, secondObj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new InvalidFieldValidationException("Invalid field validation", e);
        }
    }
}

