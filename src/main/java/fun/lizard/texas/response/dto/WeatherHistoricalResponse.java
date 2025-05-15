package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherHistoricalResponse {

    private Double latitude;
    private Double longitude;
    private String description;
    private String iconClass;
    private Integer maxTemperature;
    private Integer minTemperature;
    private String sunrise;
    private String sunset;
    private Double precipitationSum;
    private Integer windSpeed;
    private Integer windDirection;
    private Integer humidity;
    private Integer cloudCover;
}
