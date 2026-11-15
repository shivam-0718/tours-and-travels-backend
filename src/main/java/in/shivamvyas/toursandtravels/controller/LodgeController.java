package in.shivamvyas.toursandtravels.controller;

import in.shivamvyas.toursandtravels.dto.lodge.LodgeRequest;
import in.shivamvyas.toursandtravels.dto.lodge.LodgeResponse;
import in.shivamvyas.toursandtravels.service.LodgeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/lodges")
public class LodgeController {
    private final LodgeService lodgeService;

    @Autowired
    public LodgeController(LodgeService lodgeService) {
        this.lodgeService = lodgeService;
    }

    @PostMapping
    public ResponseEntity<LodgeResponse> addLodging(@RequestBody @Valid LodgeRequest request) {
        LodgeResponse response = lodgeService.registerLodge(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LodgeResponse> getLodgeById(@PathVariable("id") Long id) {
        LodgeResponse response = lodgeService.getLodgeById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LodgeResponse>> getAllLodgings() {
        List<LodgeResponse> lodgeLists = lodgeService.fetchLodges();
        return ResponseEntity.ok(lodgeLists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LodgeResponse> updateLodge(@PathVariable Long id, @RequestBody @Valid LodgeRequest request) {
        LodgeResponse response = lodgeService.updateLodge(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeLodge(@PathVariable Long id) {
        String response = lodgeService.deleteLodge(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
