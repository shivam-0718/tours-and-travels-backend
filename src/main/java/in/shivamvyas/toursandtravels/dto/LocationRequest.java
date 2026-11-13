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
    @Size(min = 3, max = 50, message = "Source Location should be between 3 characters and 50 characters.")
    @NotBlank(message = "Source location name cannot be empty.")
    private String fromLocation;

    @Size(min = 3, max = 50, message = "Destination Location should be between 3 characters and 50 characters.")
    @NotBlank(message = "Destination location name cannot be empty.")
    private String toLocation;

    @Size(min = 3, max = 50, message = "Country name should be between 3 characters and 50 characters.")
    @NotBlank(message = "Country name cannot be empty. ")
    private String country;

    @Size(min = 1, max = 500, message = "Description should be between 1 character and 500 characters")
    @NotBlank(message = "Description cannot be empty. ")
    private String description;

    @NotBlank(message = "Estimated travel time cannot be empty. Add the estimated travel time.")
    private String estimatedTravelTime;
}
