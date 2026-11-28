package in.shivamvyas.toursandtravels.controller;

import in.shivamvyas.toursandtravels.dto.tour.TourResponse;
import in.shivamvyas.toursandtravels.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Read-only tour browsing that needs no login ("/public/**" is open in SecurityConfig).
 * Create, update and delete stay under /admin/tours, admin only.
 */
@RestController
@RequestMapping("/public/tours")
public class PublicTourController {
    private final TourService tourService;

    @Autowired
    public PublicTourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public ResponseEntity<List<TourResponse>> getAllTours() {
        return ResponseEntity.ok(tourService.fetchTours());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> getTourById(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.getTourById(id));
    }
}
