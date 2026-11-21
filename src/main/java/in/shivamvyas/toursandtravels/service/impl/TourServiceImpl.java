package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.entities.Location;
import in.shivamvyas.toursandtravels.entities.Lodge;
import in.shivamvyas.toursandtravels.entities.Tour;
import in.shivamvyas.toursandtravels.entities.Transport;
import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import in.shivamvyas.toursandtravels.repo.LocationRepo;
import in.shivamvyas.toursandtravels.repo.LodgeRepo;
import in.shivamvyas.toursandtravels.repo.TourRepo;
import in.shivamvyas.toursandtravels.repo.TransportRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TourServiceImpl {
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
}
