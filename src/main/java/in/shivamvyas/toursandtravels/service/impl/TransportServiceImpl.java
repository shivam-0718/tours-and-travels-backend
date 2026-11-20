package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.dto.transport.TransportRequest;
import in.shivamvyas.toursandtravels.dto.transport.TransportResponse;
import in.shivamvyas.toursandtravels.entities.Transport;
import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import in.shivamvyas.toursandtravels.repo.TransportRepo;
import in.shivamvyas.toursandtravels.service.TransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TransportServiceImpl implements TransportService {
    private final TransportRepo transportRepo;

    @Autowired
    public TransportServiceImpl(TransportRepo transportRepo) {
        this.transportRepo = transportRepo;
    }

    @Override
    @Transactional
    public TransportResponse registerTransport(TransportRequest request) {
        Transport transport = requestToEntity(request);
        log.info("Adding a new transport....in progress......");
        Transport savedTransport = transportRepo.save(transport);
        log.info("Transport added successfully with id: {}", savedTransport.getId());
        return entityToResponse(savedTransport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransportResponse> fetchTransports() {
        log.info("Fetching all transports...");
        List<Transport> transportsList = transportRepo.findAll();
        List<TransportResponse> transportResponseList = new ArrayList<>();

        for(Transport transport : transportsList) {
            transportResponseList.add(entityToResponse(transport));
        }

        log.info("All transports fetched successfully...");
        return transportResponseList;
    }

    @Override
    @Transactional
    public TransportResponse updateTransport(Long id, TransportRequest request) {
        log.info("Updating transport with id: {}", id);
        Transport transport = findOrThrow(id);

        transport.setTransportName(request.getTransportName());
        transport.setTransportType(request.getTransportType());
        transport.setEstimatedTravelTime(request.getEstimatedTravelTime());
        transport.setTransportDescription(request.getTransportDescription());

        log.info("Transport with id number {} updated successfully", id);
        return entityToResponse(transport);
    }

    @Override
    @Transactional
    public String deleteTransport(Long id) {
        log.info("Deleting transport with id: {}", id);
        Transport transport = findOrThrow(id);
        transportRepo.delete(transport);
        log.info("Transport with id number {} has been deleted successfully", id);
        return "The transport with id " + id + " has been deleted successfully.";
    }

    @Override
    @Transactional(readOnly = true)
    public TransportResponse getTransportById(Long id) {
        log.info("Fetching transport with id: {}", id);
        Transport fetchedTransport = findOrThrow(id);
        log.info("Transport with id {} has been fetched successfully.", id);
        return entityToResponse(fetchedTransport);
    }

    /**
     * Private helper: load a Transport or throw ResourceNotFoundException (404) naming the id.
     */
    private Transport findOrThrow(Long id) {
        return transportRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found with id " + id));
    }

    /**
     * Private helper method to convert TransportRequest DTO to Transport Entity for persistence in the DB
     * @return Transport
     */
    private Transport requestToEntity(TransportRequest request) {
        return Transport.builder()
                .transportName(request.getTransportName())
                .transportType(request.getTransportType())
                .estimatedTravelTime(request.getEstimatedTravelTime())
                .transportDescription(request.getTransportDescription())
                .build();
    }

    /**
     * Private helper method to convert Transport Entity to TransportResponse DTO for giving response to the client
     * @return TransportResponse
     */
    private TransportResponse entityToResponse(Transport transport) {
        return TransportResponse.builder()
                .id(transport.getId())
                .transportName(transport.getTransportName())
                .transportType(transport.getTransportType())
                .estimatedTravelTime(transport.getEstimatedTravelTime())
                .transportDescription(transport.getTransportDescription())
                .build();
    }
}
