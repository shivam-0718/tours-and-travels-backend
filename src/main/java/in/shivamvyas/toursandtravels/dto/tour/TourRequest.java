package in.shivamvyas.toursandtravels.dto.tour;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TourRequest {
    @Size(min = 3, max = 100, message = "Tour name should be between 3 characters and 100 characters.")
    @NotBlank(message = "Tour name cannot be empty.")
    private String tourName;

    @Size(max = 1000, message = "Tour description can be up to 1000 characters.")
    @NotBlank(message = "Tour description cannot be empty.")
    private String tourDescription;

    @Size(min = 3, max = 100, message = "Tour guide name should be between 3 characters and 100 characters.")
    @NotBlank(message = "Tour guide name cannot be empty.")
    private String tourGuide;

    @FutureOrPresent(message = "Start date cannot be in the past.")
    @NotNull(message = "Start date is required.")
    private LocalDate startDate;

    @Future(message = "End date must be in the future.")
    @NotNull(message = "End date is required.")
    private LocalDate endDate;

    private List<@NotBlank(message = "Meal name cannot be empty.") String> meals;

    private List<@NotBlank(message = "Activity name cannot be empty.") String> activities;

    @DecimalMin(value = "0.0", message = "Price cannot be negative.")
    @Digits(integer = 8, fraction = 2, message = "Price can have up to 8 digits and 2 decimal places.")
    @NotNull(message = "Price is required.")
    private BigDecimal price;

    @Min(value = 0, message = "Tickets available cannot be negative.")
    @NotNull(message = "Tickets available is required.")
    private Integer ticketsAvailable;

    @NotNull(message = "Location id is required.")
    private Long locationId;

    @NotNull(message = "Lodge id is required.")
    private Long lodgeId;

    @NotNull(message = "Transport id is required.")
    private Long transportId;
}
