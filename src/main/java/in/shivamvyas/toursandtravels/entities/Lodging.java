package in.shivamvyas.toursandtravels.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import in.shivamvyas.toursandtravels.enums.LodgingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lodgings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lodging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lodging_name")
    private String lodgingName;

    @Enumerated(EnumType.STRING)
    @Column(name = "lodging_type")
    private LodgingType lodgingType;

    @Column(name = "lodging_description")
    private String lodgingDescription;

    @Column(name = "lodging_address")
    private String lodgingAddress;

    @Column(name = "rating")
    private Double rating;

}
