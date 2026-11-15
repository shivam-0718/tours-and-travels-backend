package in.shivamvyas.toursandtravels.service;

import in.shivamvyas.toursandtravels.dto.lodge.LodgeRequest;
import in.shivamvyas.toursandtravels.dto.lodge.LodgeResponse;
import java.util.List;

public interface LodgeService {
    LodgeResponse registerLodge(LodgeRequest request);
    List<LodgeResponse> fetchLodges();
    LodgeResponse updateLodge(Long id, LodgeRequest request);
    String deleteLodge(Long id);
    LodgeResponse getLodgeById(Long id);
}
