package in.shivamvyas.toursandtravels.controller;

import in.shivamvyas.toursandtravels.dto.LocationRequest;
import in.shivamvyas.toursandtravels.dto.LocationResponse;
import in.shivamvyas.toursandtravels.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/locations")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationResponse> addLocation(@RequestBody @Valid LocationRequest request) {
        LocationResponse response = locationService.registerLocation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable("id") Long id) {
        LocationResponse response = locationService.getLocationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return ResponseEntity.ok(locationService.fetchLocations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation(@PathVariable Long id, @RequestBody @Valid LocationRequest request) {
        LocationResponse response = locationService.updateLocation(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeLocation(@PathVariable Long id) {
        String response = locationService.deleteLocation(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
