package in.shivamvyas.toursandtravels.exception;

public class InsufficientTicketsException extends RuntimeException {
    public InsufficientTicketsException(String message) {
        super(message);
    }
}
