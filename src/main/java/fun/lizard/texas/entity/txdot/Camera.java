package fun.lizard.texas.entity.txdot;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Camera {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cameraId;
    private String direction;
    private String icdId;
    private Double latitude;
    private Double longitude;
    private Boolean hasSnapshot;
}
