package in.shivamvyas.toursandtravels.controller;

import in.shivamvyas.toursandtravels.dto.transport.TransportRequest;
import in.shivamvyas.toursandtravels.dto.transport.TransportResponse;
import in.shivamvyas.toursandtravels.service.TransportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/transports")
public class TransportController {
    private final TransportService transportService;

    @Autowired
    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @PostMapping
    public ResponseEntity<TransportResponse> addTransport(@RequestBody @Valid TransportRequest request) {
        TransportResponse response = transportService.registerTransport(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportResponse> getTransportById(@PathVariable("id") Long id) {
        TransportResponse response = transportService.getTransportById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransportResponse>> getAllTransports() {
        List<TransportResponse> transportList = transportService.fetchTransports();
        return ResponseEntity.ok(transportList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportResponse> updateTransport(@PathVariable Long id, @RequestBody @Valid TransportRequest request) {
        TransportResponse response = transportService.updateTransport(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeTransport(@PathVariable Long id) {
        String response = transportService.deleteTransport(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
