package in.shivamvyas.toursandtravels.controller;

import in.shivamvyas.toursandtravels.dto.booking.BookingRequest;
import in.shivamvyas.toursandtravels.dto.booking.BookingResponse;
import in.shivamvyas.toursandtravels.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Any logged-in user (the rule "anyRequest().authenticated()" in SecurityConfig).
 * The user is read from the token via Authentication, never from the request body.
 */
@RestController
@RequestMapping("/customer/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> bookTour(@RequestBody @Valid BookingRequest request,
                                                    Authentication authentication) {
        BookingResponse response = bookingService.bookTour(authentication.getName(), request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getMyBookings(Authentication authentication) {
        return ResponseEntity.ok(bookingService.fetchMyBookings(authentication.getName()));
    }
}
