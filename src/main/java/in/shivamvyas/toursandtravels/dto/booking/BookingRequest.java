package in.shivamvyas.toursandtravels.dto.booking;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// No user id and no price: the user comes from the token, the price from the tour.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    @NotNull(message = "Tour id is required.")
    private Long tourId;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    @Max(value = 10, message = "Quantity cannot be more than 10.")
    private Integer quantity;
}
