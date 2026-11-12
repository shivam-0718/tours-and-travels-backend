package in.shivamvyas.toursandtravels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    @Size(min = 5, max = 30)
    @NotBlank(message = "Source Location should be between 5 characters and 30 characters")
    private String fromLocation;

    @Size(min = 5, max = 30)
    @NotBlank(message = "Destination Location should be between 5 characters and 30 characters")
    private String toLocation;

    @Size(min = 5, max = 30)
    @NotBlank(message = "Country name should be between 5 characters and 50 characters")
    private String country;

    @Size(min = 1, max = 500)
    @NotBlank(message = "Description should be between 5 characters and 50 characters")
    private String description;

    @NotBlank(message = "The estimated travel time cannot be zero")
    private String estimatedTravelTime;
}
