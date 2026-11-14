package in.shivamvyas.toursandtravels.service;

import in.shivamvyas.toursandtravels.dto.location.LocationRequest;
import in.shivamvyas.toursandtravels.dto.location.LocationResponse;
import java.util.List;

public interface LocationService {
    LocationResponse registerLocation(LocationRequest request);
    List<LocationResponse> fetchLocations();
    LocationResponse updateLocation(Long id, LocationRequest request);
    String deleteLocation(Long id);
    LocationResponse getLocationById(Long id);
}
