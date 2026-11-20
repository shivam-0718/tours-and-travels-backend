package in.shivamvyas.toursandtravels.service;

import in.shivamvyas.toursandtravels.dto.transport.TransportRequest;
import in.shivamvyas.toursandtravels.dto.transport.TransportResponse;
import java.util.List;

public interface TransportService {
    TransportResponse registerTransport(TransportRequest request);
    List<TransportResponse> fetchTransports();
    TransportResponse updateTransport(Long id, TransportRequest request);
    String deleteTransport(Long id);
    TransportResponse getTransportById(Long id);
}
