package in.shivamvyas.toursandtravels.service.impl;

import in.shivamvyas.toursandtravels.dto.lodge.LodgeRequest;
import in.shivamvyas.toursandtravels.dto.lodge.LodgeResponse;
import in.shivamvyas.toursandtravels.entities.Lodge;
import in.shivamvyas.toursandtravels.exception.ResourceNotFoundException;
import in.shivamvyas.toursandtravels.repo.LodgeRepo;
import in.shivamvyas.toursandtravels.service.LodgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LodgeServiceImpl implements LodgeService {
    private final LodgeRepo lodgeRepo;

    @Autowired
    public LodgeServiceImpl(LodgeRepo lodgeRepo) {
        this.lodgeRepo = lodgeRepo;
    }

    @Override
    @Transactional
    public LodgeResponse registerLodge(LodgeRequest request) {
        Lodge lodge = requestToEntity(request);
        log.info("Adding a new lodge....in progress......");
        Lodge savedLodge = lodgeRepo.save(lodge);
        log.info("Lodge added successfully with id: {}", savedLodge.getId());
        return entityToResponse(savedLodge);
    }

    @Override
    public List<LodgeResponse> fetchLodges() {
        log.info("Fetching all lodges...");
        List<Lodge> lodgesList = lodgeRepo.findAll();
        List<LodgeResponse> lodgeResponseList = new ArrayList<>();

        for(Lodge lodge : lodgesList) {
            lodgeResponseList.add(entityToResponse(lodge));
        }

        log.info("All lodges fetched successfully...");
        return lodgeResponseList;
    }

    @Override
    @Transactional
    public LodgeResponse updateLodge(Long id, LodgeRequest request) {
        log.info("Updating lodge with id: {}", id);
        Lodge lodge = findOrThrow(id);

        lodge.setLodgeName(request.getLodgeName());
        lodge.setLodgingType(request.getLodgingType());
        lodge.setLodgeDescription(request.getLodgeDescription());
        lodge.setLodgeAddress(request.getLodgeAddress());
        lodge.setRating(request.getRating());

        lodgeRepo.save(lodge);
        log.info("Lodge with id number {} updated successfully", id);
        return entityToResponse(lodge);
    }

    @Override
    @Transactional
    public String deleteLodge(Long id) {
        log.info("Deleting lodge with id: {}", id);
        Lodge lodge = findOrThrow(id);
        lodgeRepo.delete(lodge);
        log.info("Lodge with id number {} has been deleted successfully", id);
        return "The lodge with id " + id + " has been deleted successfully.";
    }

    @Override
    @Transactional(readOnly = true)
    public LodgeResponse getLodgeById(Long id) {
        log.info("Fetching lodge with id: {}", id);
        Lodge fetchedLodge = findOrThrow(id);
        log.info("Lodge with id {} has been fetched successfully.", id);
        return entityToResponse(fetchedLodge);
    }

    /**
     * Private helper: load a Lodge or throw ResourceNotFoundException (404) naming the id.
     */
    private Lodge findOrThrow(Long id) {
        return lodgeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lodge not found with id " + id));
    }

    /**
     * Private helper method to convert LodgeRequest DTO to Lodge Entity for persistence in the DB
     * @return Lodge
     */
    private Lodge requestToEntity(LodgeRequest request) {
        return Lodge.builder()
                .lodgeName(request.getLodgeName())
                .lodgingType(request.getLodgingType())
                .lodgeAddress(request.getLodgeAddress())
                .lodgeDescription(request.getLodgeDescription())
                .rating(request.getRating())
                .build();
    }

    /**
     * Private helper method to convert Lodge Entity to LodgeResponse DTO for giving response to the client
     * @return LodgeResponse
     */
    private LodgeResponse entityToResponse(Lodge lodge) {
        return LodgeResponse.builder()
                .id(lodge.getId())
                .lodgeName(lodge.getLodgeName())
                .lodgingType(lodge.getLodgingType())
                .lodgeAddress(lodge.getLodgeAddress())
                .lodgeDescription(lodge.getLodgeDescription())
                .rating(lodge.getRating())
                .build();
    }
}
