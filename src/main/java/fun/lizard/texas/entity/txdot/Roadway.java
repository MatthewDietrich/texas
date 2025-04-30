package fun.lizard.texas.entity.txdot;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Roadway {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roadwayId;
    private String name;
    private String direction;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
}
