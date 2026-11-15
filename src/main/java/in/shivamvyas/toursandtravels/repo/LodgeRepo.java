package in.shivamvyas.toursandtravels.repo;

import in.shivamvyas.toursandtravels.entities.Lodge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LodgeRepo extends JpaRepository<Lodge, Long> {
}
