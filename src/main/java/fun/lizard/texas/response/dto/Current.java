package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Current {

    private String description;
    private Integer windSpeed;
    private Integer windDirection;
    private Integer precipitation;
    private Integer cloudCover;
    private Integer rain;
    private Integer humidity;
    private Integer temperature;
    private Integer apparentTemperature;
    private String iconClass;
    private Double pressureMsl;
}
