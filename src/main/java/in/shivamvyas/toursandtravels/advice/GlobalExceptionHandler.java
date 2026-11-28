package in.shivamvyas.toursandtravels.advice;

import in.shivamvyas.toursandtravels.exception.DuplicateResourceException;
import in.shivamvyas.toursandtravels.exception.InsufficientTicketsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Central place that turns exceptions thrown from any controller into a JSON
 * {@link ErrorDetails} body with the right HTTP status.
 * {@code @RestControllerAdvice} = {@code @ControllerAdvice} + {@code @ResponseBody}.
 * Spring picks the most specific matching {@code @ExceptionHandler}; the
 * {@code Exception.class} handler at the bottom is the fallback.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 404: a requested Location, Lodging, Transport, etc. does not exist.
     * Thrown by the service layer as {@link ResourceNotFoundException}.
     * {@code WebRequest} is injected by Spring; {@code getDescription(false)}
     * returns the request path as "uri=/..." (false = no client/session info).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails body = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        log.debug("Entity not found: {} ", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * 409: the resource already exists (e.g. an email that is already registered).
     * Thrown by the service layer as {@link DuplicateResourceException}.
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDetails> handleDuplicate(DuplicateResourceException ex, WebRequest request) {
        ErrorDetails body = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        log.debug("Duplicate resource: {}", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     * 401: wrong email or password (thrown by AuthenticationManager during login).
     * One fixed message for every case, so a caller cannot tell which emails exist.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleAuthentication(AuthenticationException ex, WebRequest request) {
        ErrorDetails body = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message("Invalid email or password.")
                .details(request.getDescription(false))
                .build();
        log.debug("Authentication failed: {}", ex.getClass().getSimpleName());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 403: logged in, but the role is not allowed (thrown by @PreAuthorize inside the controller call).
     * Without this handler the catch-all below would turn it into a 500.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        ErrorDetails body = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message("You do not have permission to do this.")
                .details(request.getDescription(false))
                .build();
        log.debug("Access denied: {}", request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    /**
     * 409: not enough tickets left for the requested quantity.
     */
    @ExceptionHandler(InsufficientTicketsException.class)
    public ResponseEntity<ErrorDetails> handleInsufficientTickets(InsufficientTicketsException ex, WebRequest request) {
        ErrorDetails body = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message(ex.getMessage())
                .details(request.getDescription(false))
                .build();
        log.debug("Insufficient tickets: {}", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     * 400: Spring could not read the request body into the Java object.
     * {@code HttpMessageNotReadableException} is raised while parsing JSON, before
     * the controller method runs, so only an advice class can handle it.
     * Typical causes: malformed JSON, wrong field type, or a value that is not in
     * an enum (e.g. lodgingType = "CASTLE").
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleUnreadableData(HttpMessageNotReadableException ex, WebRequest request) {
        ErrorDetails body = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message("Invalid request body. Check the JSON format and values passed carefully.")
                .details(request.getDescription(false))
                .build();
        log.debug("Unreadable request body: {}", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Thrown when a single @Valid @RequestBody object fails bean validation (e.g. LocationRequest).
     * Returns 400 with a field name -> validation message map.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentValidationException(MethodArgumentNotValidException mave) {
        Map<String, String> errors = new HashMap<>();
        mave.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * 500: fallback for any exception no other handler matches.
     * The full stack trace is logged on the server; the client should only see a safe message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
        ErrorDetails body = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now())
                .message("Something went wrong. Please try again later.")
                .details(request.getDescription(false))
                .build();
        log.error("Unexpected error: {}", request.getDescription(false), ex);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
