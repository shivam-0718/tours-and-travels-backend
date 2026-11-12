package in.shivamvyas.toursandtravels.repo;

import in.shivamvyas.toursandtravels.entities.Lodging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LodgingRepo extends JpaRepository<Lodging, Long> {
}
