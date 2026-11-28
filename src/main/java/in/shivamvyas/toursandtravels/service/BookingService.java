package in.shivamvyas.toursandtravels.service;

import in.shivamvyas.toursandtravels.dto.booking.BookingRequest;
import in.shivamvyas.toursandtravels.dto.booking.BookingResponse;
import java.util.List;

public interface BookingService {
    BookingResponse bookTour(String userEmail, BookingRequest request);
    List<BookingResponse> fetchMyBookings(String userEmail);
}
