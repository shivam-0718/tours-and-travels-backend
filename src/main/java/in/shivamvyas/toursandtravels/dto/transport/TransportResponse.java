package in.shivamvyas.toursandtravels.dto.transport;

import in.shivamvyas.toursandtravels.enums.TransportType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransportResponse {
    private Long id;
    private String transportName;
    private TransportType transportType;
    private String estimatedTravelTime;
    private String transportDescription;
}
