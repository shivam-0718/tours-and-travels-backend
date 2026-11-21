package in.shivamvyas.toursandtravels.repo;

import in.shivamvyas.toursandtravels.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TourRepo extends JpaRepository<Tour, Long> {

    // Loads every tour together with its location, lodge and transport in one query (avoids N+1)
    @Query("SELECT t FROM Tour t " +
            "JOIN FETCH t.location " +
            "JOIN FETCH t.lodge " +
            "JOIN FETCH t.transport")
    List<Tour> findAllToursWithDetails();

    // Same, for a single tour by id
    @Query("SELECT t FROM Tour t " +
            "JOIN FETCH t.location " +
            "JOIN FETCH t.lodge " +
            "JOIN FETCH t.transport " +
            "WHERE t.id = :tourId")
    Optional<Tour> findTourByIdWithDetails(@Param("tourId") Long tourId);
}
