package in.shivamvyas.toursandtravels.dto.lodge;

import in.shivamvyas.toursandtravels.enums.LodgingType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LodgeRequest {
    @Size(min = 3, max = 100, message = "Lodging name should be between 3 characters and 100 characters.")
    @NotBlank(message = "Lodging name cannot be empty.")
    private String lodgeName;

    @NotNull(message = "Lodging Type is required.")
    private LodgingType lodgingType;

    @Size(max = 500, message = "Lodging Description can be up to 500 characters.")
    @NotBlank(message = "Lodging Description cannot be empty.")
    private String lodgeDescription;

    @Size(max = 255, message = "Lodging Address can be up to 255 characters.")
    @NotBlank(message = "Lodging Address cannot be empty.")
    private String lodgeAddress;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double rating;
}
