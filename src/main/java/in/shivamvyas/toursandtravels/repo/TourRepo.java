package in.shivamvyas.toursandtravels.repo;

import in.shivamvyas.toursandtravels.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    // Atomic reservation: the database checks and subtracts in one statement, so two people
    // booking the last tickets at the same moment cannot both succeed. Returns rows changed (1 or 0).
    @Modifying
    @Query("UPDATE Tour t SET t.ticketsAvailable = t.ticketsAvailable - :qty " +
            "WHERE t.id = :tourId AND t.ticketsAvailable >= :qty")
    int reserveTickets(@Param("tourId") Long tourId, @Param("qty") int qty);
}
