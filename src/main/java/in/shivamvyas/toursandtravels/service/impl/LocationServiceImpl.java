package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.dto.LocationRequest;
import in.shivamvyas.toursandtravels.dto.LocationResponse;
import in.shivamvyas.toursandtravels.entities.Location;
import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import in.shivamvyas.toursandtravels.repo.LocationRepo;
import in.shivamvyas.toursandtravels.service.LocationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepo locationRepo;

    @Autowired
    public LocationServiceImpl(LocationRepo locationRepo) {
        this.locationRepo = locationRepo;
    }

    @Override
    @Transactional
    public LocationResponse registerLocation(LocationRequest request) {
        Location location = requestToEntity(request);
        log.info("Adding a new location....in progress......");
        Location savedLocation = locationRepo.save(location);
        log.info("Location added successfully with id: {}", savedLocation.getId());
        return entityToResponse(savedLocation);
    }

    @Override
    public List<LocationResponse> fetchLocations() {
        log.info("Fetching all locations...");
        List<Location> locationsList = locationRepo.findAll();
        List<LocationResponse> locationResponseList = new ArrayList<>();

        for(Location location : locationsList) {
            locationResponseList.add(entityToResponse(location));
        }

        log.info("All locations fetched successfully...");
        return locationResponseList;
    }

    @Override
    @Transactional
    public LocationResponse updateLocation(Long id, LocationRequest request) {
        log.info("Updating location with id: {}", id);
        Location location = findOrThrow(id);

        location.setFromLocation(request.getFromLocation());
        location.setToLocation(request.getToLocation());
        location.setCountry(request.getCountry());
        location.setDescription(request.getDescription());
        location.setEstimatedTravelTime(request.getEstimatedTravelTime());

        locationRepo.save(location);
        log.info("Location with id number {} updated successfully", id);
        return entityToResponse(location);
    }

    @Override
    @Transactional
    public String deleteLocation(Long id) {
        log.info("Deleting location with id: {}", id);
        Location location = findOrThrow(id);
        locationRepo.delete(location);
        log.info("Location with id number {} has been deleted successfully", id);
        return "The location with id " + id + " has been deleted successfully.";
    }

    @Override
    public LocationResponse getLocationById(Long id) {
        log.info("Fetching location with id: {}", id);
        Location fetchedLocation = findOrThrow(id);
        log.info("Location with id {} has been fetched successfully.", id);
        return entityToResponse(fetchedLocation);
    }

    /**
     * Private helper: load a Location or throw ResourceNotFoundException (404) naming the id.
     */
    private Location findOrThrow(Long id) {
        return locationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id " + id));
    }

    /**
     * Private helper method to convert LocationRequest DTO to Location Entity for persistence in the DB
     * @return Location
     */
    private Location requestToEntity(LocationRequest request) {
        return Location.builder()
                .fromLocation(request.getFromLocation())
                .toLocation(request.getToLocation())
                .country(request.getCountry())
                .description(request.getDescription())
                .estimatedTravelTime(request.getEstimatedTravelTime())
                .build();
    }

    /**
     * Private helper method to convert Location Entity to LocationResponse DTO for giving response to the client
     * @return LocationResponse
     */
    private LocationResponse entityToResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .fromLocation(location.getFromLocation())
                .toLocation(location.getToLocation())
                .country(location.getCountry())
                .description(location.getDescription())
                .estimatedTravelTime(location.getEstimatedTravelTime())
                .build();
    }
}
