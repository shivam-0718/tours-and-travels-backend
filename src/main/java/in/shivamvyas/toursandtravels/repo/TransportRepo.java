package in.shivamvyas.toursandtravels.repo;

import in.shivamvyas.toursandtravels.entities.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepo extends JpaRepository<Transport, Long> {
}
