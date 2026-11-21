package in.shivamvyas.toursandtravels.dto.tour;

import in.shivamvyas.toursandtravels.dto.location.LocationResponse;
import in.shivamvyas.toursandtravels.dto.lodge.LodgeResponse;
import in.shivamvyas.toursandtravels.dto.transport.TransportResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class TourResponse {
    private Long id;
    private String tourName;
    private String tourDescription;
    private String tourGuide;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> meals;
    private List<String> activities;
    private List<String> tourImages;
    private BigDecimal price;
    private Integer ticketsAvailable;
    private LocationResponse location;
    private LodgeResponse lodge;
    private TransportResponse transport;
}
