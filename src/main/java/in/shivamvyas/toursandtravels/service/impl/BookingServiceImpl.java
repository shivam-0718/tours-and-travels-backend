package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.dto.booking.BookingRequest;
import in.shivamvyas.toursandtravels.dto.booking.BookingResponse;
import in.shivamvyas.toursandtravels.entities.Booking;
import in.shivamvyas.toursandtravels.entities.Tour;
import in.shivamvyas.toursandtravels.entities.Users;
import in.shivamvyas.toursandtravels.enums.BookingStatus;
import in.shivamvyas.toursandtravels.exception.InsufficientTicketsException;
import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import in.shivamvyas.toursandtravels.repo.BookingRepo;
import in.shivamvyas.toursandtravels.repo.TourRepo;
import in.shivamvyas.toursandtravels.repo.UserRepo;
import in.shivamvyas.toursandtravels.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepo bookingRepo;
    private final TourRepo tourRepo;
    private final UserRepo userRepo;

    @Autowired
    public BookingServiceImpl(BookingRepo bookingRepo, TourRepo tourRepo, UserRepo userRepo) {
        this.bookingRepo = bookingRepo;
        this.tourRepo = tourRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public BookingResponse bookTour(String userEmail, BookingRequest request) {
        log.info("Booking a tour....in progress......");
        Users user = findUserOrThrow(userEmail);
        Tour tour = tourRepo.findById(request.getTourId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id " + request.getTourId()));

        int quantity = request.getQuantity();

        // One UPDATE both checks and subtracts. 0 rows changed = not enough tickets.
        if (tourRepo.reserveTickets(tour.getId(), quantity) == 0) {
            throw new InsufficientTicketsException("Not enough tickets available for this tour.");
        }

        // The price is taken from the tour, never from the client.
        BigDecimal total = tour.getPrice().multiply(BigDecimal.valueOf(quantity));

        // No payment step yet, so a reserved booking is final. With Stripe this becomes PENDING first.
        Booking booking = Booking.builder()
                .user(user)
                .tour(tour)
                .quantity(quantity)
                .totalPrice(total)
                .status(BookingStatus.SUCCESS)
                .build();
        Booking saved = bookingRepo.save(booking);
        log.info("Booking created with id: {}", saved.getId());
        return entityToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> fetchMyBookings(String userEmail) {
        Users user = findUserOrThrow(userEmail);
        List<BookingResponse> responses = new ArrayList<>();
        for (Booking booking : bookingRepo.findByUserIdWithTour(user.getId())) {
            responses.add(entityToResponse(booking));
        }
        return responses;
    }

    private Users findUserOrThrow(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private BookingResponse entityToResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .tourId(booking.getTour().getId())
                .tourName(booking.getTour().getTourName())
                .quantity(booking.getQuantity())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .bookedAt(booking.getBookedAt())
                .build();
    }
}
