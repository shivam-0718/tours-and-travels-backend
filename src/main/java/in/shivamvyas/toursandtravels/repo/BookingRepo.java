package in.shivamvyas.toursandtravels.repo;

import in.shivamvyas.toursandtravels.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Long> {

    // one query that also loads each tour (avoids N+1), newest first
    @Query("SELECT b FROM Booking b JOIN FETCH b.tour " +
            "WHERE b.user.id = :userId ORDER BY b.bookedAt DESC")
    List<Booking> findByUserIdWithTour(@Param("userId") Long userId);
}
