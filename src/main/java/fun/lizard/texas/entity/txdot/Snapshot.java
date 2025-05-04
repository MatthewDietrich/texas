package fun.lizard.texas.entity.txdot;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Snapshot {

    @Id
    private String id;
    private String cameraIcdId;
    private LocalDateTime timestamp;
    private byte[] image;
}
