package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleSnapshot {

    private String cameraId;
    private double latitude;
    private double longitude;
    private String snapshot;
    private String cityName;
    private String districtAbbreviation;
    private String countyName;
    private String snapshotTime;
}
