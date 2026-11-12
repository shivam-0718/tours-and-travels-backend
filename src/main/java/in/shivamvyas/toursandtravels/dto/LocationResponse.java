package in.shivamvyas.toursandtravels.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationResponse {
    private Long id;
    private String fromLocation;
    private String toLocation;
    private String country;
    private String description;
    private String estimatedTravelTime;
}
