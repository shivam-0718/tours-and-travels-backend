package in.shivamvyas.toursandtravels.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transport")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transport_name")
    private String transportName;

    @Column(name = "transport_type")
    private String transportType;

    @Column(name = "estimated_travel_time")
    private String estimatedTravelTime;

    @Column(name = "transport_description")
    private String transportDescription;
}
