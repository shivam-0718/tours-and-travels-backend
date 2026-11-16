package in.shivamvyas.toursandtravels.dto.transport;

import in.shivamvyas.toursandtravels.enums.TransportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportRequest {
    @Size(min = 3, max = 100, message = "Transport name should be between 3 characters and 100 characters.")
    @NotBlank(message = "Transport name cannot be empty.")
    private String transportName;

    @NotNull(message = "Transport type is required.")
    private TransportType transportType;

    @Size(min = 3, max = 20, message = "Estimated Travel time should be between 3 characters and 20 characters.")
    @NotBlank(message = "Estimated Travel time cannot be empty.")
    private String estimatedTravelTime;

    @Size(min = 3, max = 255, message = "Transport description should be between 3 characters and 255 characters.")
    @NotBlank(message = "Transport description cannot be empty.")
    private String transportDescription;
}
