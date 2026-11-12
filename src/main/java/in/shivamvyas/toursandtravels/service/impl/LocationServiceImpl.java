package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.dto.LocationRequest;
import in.shivamvyas.toursandtravels.dto.LocationResponse;
import in.shivamvyas.toursandtravels.entities.Location;
import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import in.shivamvyas.toursandtravels.repo.LocationRepo;
import in.shivamvyas.toursandtravels.service.LocationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class LocationServiceImpl implements LocationService {
    private static final Logger logger = Logger.getLogger(LocationServiceImpl.class.getName());

    @Autowired
    private LocationRepo locationRepo;

    @Override
    @Transactional
    public LocationResponse registerLocation(LocationRequest request) {
        Location location = requestToEntity(request);
        logger.info("Adding a new location....in progress......");
        Location savedLocation = locationRepo.save(location);
        logger.info("Location added successfully with id: " + savedLocation.getId());
        return entityToResponse(savedLocation);
    }

    @Override
    public List<LocationResponse> fetchLocations() {
        logger.info("Fetching all locations");
        List<Location> locationsList = locationRepo.findAll();
        List<LocationResponse> locationResponseList = new ArrayList<>();

        for(Location location : locationsList) {
            locationResponseList.add(entityToResponse(location));
        }

        logger.info("All locations fetched successfully");
        return locationResponseList;
    }

    @Override
    @Transactional
    public LocationResponse updateLocation(Long id, LocationRequest request) {
        logger.info("Updating location with ID: " + id);
        Optional<Location> optional = locationRepo.findById(id);
        if(optional.isPresent()) {
            Location location = optional.get();

            location.setFromLocation(request.getFromLocation());
            location.setToLocation(request.getToLocation());
            location.setCountry(request.getCountry());
            location.setDescription(request.getDescription());
            location.setEstimatedTravelTime(request.getEstimatedTravelTime());

            locationRepo.save(location);
            logger.info("Location updated successfully");
            return entityToResponse(location);
        }
        throw new ResourceNotFoundException("Location with given id is not available. Try again with correct id.");
    }

    @Override
    @Transactional
    public String deleteLocation(Long id) {
        logger.info("Deleting location with ID: " + id);
        Optional<Location> optional = locationRepo.findById(id);
        if(optional.isPresent()) {
            locationRepo.delete(optional.get());
            logger.info("Location with ID " + id +  " has been deleted successfully");
            return "The location with id " + id + " has been deleted successfully.";
        }

        throw new ResourceNotFoundException("Location with given id is not available. Try again with correct id.");
    }

    @Override
    public LocationResponse getLocationById(Long id) {
        logger.info("Fetching location with ID: " + id);
        Optional<Location> optional = locationRepo.findById(id);
        if(optional.isPresent()) {
            Location fetchedLocation = optional.get();
            return entityToResponse(fetchedLocation);
        }
        throw new ResourceNotFoundException("Location with given id is not available. Try again with correct id.");
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
