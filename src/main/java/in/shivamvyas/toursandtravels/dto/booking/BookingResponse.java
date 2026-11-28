package in.shivamvyas.toursandtravels.dto.booking;

import in.shivamvyas.toursandtravels.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BookingResponse {
    private Long id;
    private Long tourId;
    private String tourName;
    private Integer quantity;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private LocalDateTime bookedAt;
}
