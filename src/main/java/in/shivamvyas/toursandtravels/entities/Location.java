package in.shivamvyas.toursandtravels.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_location")
    private String fromLocation;

    @Column(name = "to_location")
    private String toLocation;

    @Column(name = "country")
    private String country;

    @Column(name = "description")
    private String description;

    @Column(name = "estimated_travel_time")
    private String estimatedTravelTime;
}
