package fun.lizard.texas.response.weather;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherResponse {

    private Double latitude;
    private Double longitude;
    private String description;
    private Double windSpeed;
    private Integer windDirection;
    private Integer precipitation;
    private Integer cloudCover;
    private Integer rain;
    private Integer humidity;
    private Double temperature;
    private Double apparentTemperature;
}
