package in.shivamvyas.toursandtravels.dto.lodge;

import in.shivamvyas.toursandtravels.enums.LodgingType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LodgeResponse {
    private Long id;
    private String lodgeName;
    private LodgingType lodgingType;
    private String lodgeDescription;
    private String lodgeAddress;
    private Double rating;
}
