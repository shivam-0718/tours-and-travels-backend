package in.shivamvyas.toursandtravels.controller;

import in.shivamvyas.toursandtravels.dto.tour.TourRequest;
import in.shivamvyas.toursandtravels.dto.tour.TourResponse;
import in.shivamvyas.toursandtravels.service.TourService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/tours")
public class TourController {
    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    public ResponseEntity<TourResponse> addTour(@RequestBody @Valid TourRequest request) {
        TourResponse response = tourService.registerTour(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> getTourById(@PathVariable("id") Long id) {
        TourResponse response = tourService.getTourById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TourResponse>> getAllTours() {
        List<TourResponse> tourList = tourService.fetchTours();
        return ResponseEntity.ok(tourList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourResponse> updateTour(@PathVariable Long id, @RequestBody @Valid TourRequest request) {
        TourResponse response = tourService.updateTour(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeTour(@PathVariable Long id) {
        String response = tourService.deleteTour(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
