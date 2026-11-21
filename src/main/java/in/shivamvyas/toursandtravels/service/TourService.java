package in.shivamvyas.toursandtravels.service;

import in.shivamvyas.toursandtravels.dto.tour.TourRequest;
import in.shivamvyas.toursandtravels.dto.tour.TourResponse;
import java.util.List;

public interface TourService {
    TourResponse registerTour(TourRequest request);
    List<TourResponse> fetchTours();
    TourResponse updateTour(Long id, TourRequest request);
    String deleteTour(Long id);
    TourResponse getTourById(Long id);
}
