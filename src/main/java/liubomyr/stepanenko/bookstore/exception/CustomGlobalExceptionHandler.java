package liubomyr.stepanenko.bookstore.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList());
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(RegistrationException.class)
    protected ResponseEntity<Object> handleRegistrationException(RegistrationException ex) {
        return new ResponseEntity<>(getConflictResponseBody(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(getConflictResponseBody(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ShippingAddressException.class)
    protected ResponseEntity<Object> handleShippingAddressException(ShippingAddressException ex) {
        return new ResponseEntity<>(getConflictResponseBody(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyShoppingCartException.class)
    protected ResponseEntity<Object> handleShippingAddressException(EmptyShoppingCartException ex) {
        return new ResponseEntity<>(getConflictResponseBody(ex), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidStatusException.class)
    protected ResponseEntity<Object> handleInvalidStatusException(InvalidStatusException ex) {
        return new ResponseEntity<>(getConflictResponseBody(ex), HttpStatus.CONFLICT);
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            String message = e.getDefaultMessage();
            return String.format("%s %s", field, message);
        }
        return e.getDefaultMessage();
    }

    private Map<String, Object> getConflictResponseBody(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.CONFLICT);
        body.put("message", ex.getMessage());
        return body;
    }
}
