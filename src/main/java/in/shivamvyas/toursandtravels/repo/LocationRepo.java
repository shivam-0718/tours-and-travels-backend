package in.shivamvyas.toursandtravels.repo;

import in.shivamvyas.toursandtravels.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<Location, Long> {

}
