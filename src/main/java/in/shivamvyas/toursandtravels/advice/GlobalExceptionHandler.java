package in.shivamvyas.toursandtravels.advice;

import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;

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
     * 500: fallback for any exception no other handler matches.
     * The full stack trace is logged on the server (exception passed as the last
     * argument to log.error); the client should only see a safe message.
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
