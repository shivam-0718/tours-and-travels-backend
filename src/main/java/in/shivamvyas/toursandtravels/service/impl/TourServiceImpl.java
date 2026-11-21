package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.dto.location.LocationResponse;
import in.shivamvyas.toursandtravels.dto.lodge.LodgeResponse;
import in.shivamvyas.toursandtravels.dto.tour.TourRequest;
import in.shivamvyas.toursandtravels.dto.tour.TourResponse;
import in.shivamvyas.toursandtravels.dto.transport.TransportResponse;
import in.shivamvyas.toursandtravels.entities.Location;
import in.shivamvyas.toursandtravels.entities.Lodge;
import in.shivamvyas.toursandtravels.entities.Tour;
import in.shivamvyas.toursandtravels.entities.Transport;
import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import in.shivamvyas.toursandtravels.repo.LocationRepo;
import in.shivamvyas.toursandtravels.repo.LodgeRepo;
import in.shivamvyas.toursandtravels.repo.TourRepo;
import in.shivamvyas.toursandtravels.repo.TransportRepo;
import in.shivamvyas.toursandtravels.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TourServiceImpl implements TourService {
    private final TourRepo tourRepo;
    private final LocationRepo locationRepo;
    private final LodgeRepo lodgeRepo;
    private final TransportRepo transportRepo;

    @Autowired
    public TourServiceImpl(TourRepo tourRepo, LocationRepo locationRepo,
                           LodgeRepo lodgeRepo, TransportRepo transportRepo) {
        this.tourRepo = tourRepo;
        this.locationRepo = locationRepo;
        this.lodgeRepo = lodgeRepo;
        this.transportRepo = transportRepo;
    }

    @Override
    @Transactional
    public TourResponse registerTour(TourRequest request) {
        log.info("Adding a new tour....in progress......");
        Location location = findLocationOrThrow(request.getLocationId());
        Lodge lodge = findLodgeOrThrow(request.getLodgeId());
        Transport transport = findTransportOrThrow(request.getTransportId());

        Tour tour = requestToEntity(request, location, lodge, transport);
        Tour savedTour = tourRepo.save(tour);
        log.info("Tour added successfully with id: {}", savedTour.getId());
        return entityToResponse(savedTour);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TourResponse> fetchTours() {
        log.info("Fetching all tours...");
        List<Tour> toursList = tourRepo.findAllToursWithDetails();
        List<TourResponse> tourResponseList = new ArrayList<>();

        for(Tour tour : toursList) {
            tourResponseList.add(entityToResponse(tour));
        }

        log.info("All tours fetched successfully...");
        return tourResponseList;
    }

    @Override
    @Transactional
    public TourResponse updateTour(Long id, TourRequest request) {
        log.info("Updating tour with id: {}", id);
        Tour tour = findOrThrow(id);
        Location location = findLocationOrThrow(request.getLocationId());
        Lodge lodge = findLodgeOrThrow(request.getLodgeId());
        Transport transport = findTransportOrThrow(request.getTransportId());

        tour.setTourName(request.getTourName());
        tour.setTourDescription(request.getTourDescription());
        tour.setTourGuide(request.getTourGuide());
        tour.setStartDate(request.getStartDate());
        tour.setEndDate(request.getEndDate());
        tour.setPrice(request.getPrice());
        tour.setTicketsAvailable(request.getTicketsAvailable());
        tour.setLocation(location);
        tour.setLodge(lodge);
        tour.setTransport(transport);

        // change the contents of the managed lists instead of replacing the lists
        tour.getMeals().clear();
        tour.getMeals().addAll(copyOf(request.getMeals()));
        tour.getActivities().clear();
        tour.getActivities().addAll(copyOf(request.getActivities()));

        log.info("Tour with id number {} updated successfully", id);
        return entityToResponse(tour);
    }

    @Override
    @Transactional
    public String deleteTour(Long id) {
        log.info("Deleting tour with id: {}", id);
        Tour tour = findOrThrow(id);
        tourRepo.delete(tour);
        log.info("Tour with id number {} has been deleted successfully", id);
        return "The tour with id " + id + " has been deleted successfully.";
    }

    @Override
    @Transactional(readOnly = true)
    public TourResponse getTourById(Long id) {
        log.info("Fetching tour with id: {}", id);
        Tour fetchedTour = findOrThrow(id);
        log.info("Tour with id {} has been fetched successfully.", id);
        return entityToResponse(fetchedTour);
    }

    /**
     * Private helper: load a Tour together with its location, lodge and transport (JOIN FETCH)
     * or throw ResourceNotFoundException (404) naming the id.
     */
    private Tour findOrThrow(Long id) {
        return tourRepo.findTourByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tour not found with id " + id));
    }

    private Location findLocationOrThrow(Long id) {
        return locationRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id " + id));
    }

    private Lodge findLodgeOrThrow(Long id) {
        return lodgeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lodge not found with id " + id));
    }

    private Transport findTransportOrThrow(Long id) {
        return transportRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found with id " + id));
    }

    /**
     * Private helper method to convert TourRequest DTO to Tour Entity for persistence in the DB.
     * The three linked entities are loaded by the caller; images are not part of the request.
     * @return Tour
     */
    private Tour requestToEntity(TourRequest request, Location location, Lodge lodge, Transport transport) {
        return Tour.builder()
                .tourName(request.getTourName())
                .tourDescription(request.getTourDescription())
                .tourGuide(request.getTourGuide())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .meals(copyOf(request.getMeals()))
                .activities(copyOf(request.getActivities()))
                .price(request.getPrice())
                .ticketsAvailable(request.getTicketsAvailable())
                .location(location)
                .lodge(lodge)
                .transport(transport)
                .build();
    }

    /**
     * Private helper method to convert Tour Entity to TourResponse DTO for giving response to the client.
     * Must run while the transaction is open, because the lists and links are lazy.
     * @return TourResponse
     */
    private TourResponse entityToResponse(Tour tour) {
        return TourResponse.builder()
                .id(tour.getId())
                .tourName(tour.getTourName())
                .tourDescription(tour.getTourDescription())
                .tourGuide(tour.getTourGuide())
                .startDate(tour.getStartDate())
                .endDate(tour.getEndDate())
                .meals(copyOf(tour.getMeals()))
                .activities(copyOf(tour.getActivities()))
                .tourImages(copyOf(tour.getTourImages()))
                .price(tour.getPrice())
                .ticketsAvailable(tour.getTicketsAvailable())
                .location(locationToResponse(tour.getLocation()))
                .lodge(lodgeToResponse(tour.getLodge()))
                .transport(transportToResponse(tour.getTransport()))
                .build();
    }

    private LocationResponse locationToResponse(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .fromLocation(location.getFromLocation())
                .toLocation(location.getToLocation())
                .country(location.getCountry())
                .description(location.getDescription())
                .estimatedTravelTime(location.getEstimatedTravelTime())
                .build();
    }

    private LodgeResponse lodgeToResponse(Lodge lodge) {
        return LodgeResponse.builder()
                .id(lodge.getId())
                .lodgeName(lodge.getLodgeName())
                .lodgingType(lodge.getLodgingType())
                .lodgeDescription(lodge.getLodgeDescription())
                .lodgeAddress(lodge.getLodgeAddress())
                .rating(lodge.getRating())
                .build();
    }

    private TransportResponse transportToResponse(Transport transport) {
        return TransportResponse.builder()
                .id(transport.getId())
                .transportName(transport.getTransportName())
                .transportType(transport.getTransportType())
                .estimatedTravelTime(transport.getEstimatedTravelTime())
                .transportDescription(transport.getTransportDescription())
                .build();
    }

    /**
     * Copies a list into a new ArrayList; a missing (null) list becomes an empty one.
     */
    private List<String> copyOf(List<String> source) {
        return source == null ? new ArrayList<>() : new ArrayList<>(source);
    }
}
