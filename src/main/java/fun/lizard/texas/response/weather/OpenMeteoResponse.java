package fun.lizard.texas.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenMeteoResponse {

    private Double latitude;
    private Double longitude;
    private Current current;

    @Data
    @NoArgsConstructor
    public static class Current {

        @JsonProperty("temperature_2m")
        private Double temperature;
        @JsonProperty("relative_humidity_2m")
        private Integer relativeHumidity;
        @JsonProperty("apparent_temperature")
        private Double apparentTemperature;
        private Integer precipitation;
        @JsonProperty("wind_speed_10m")
        private Double windSpeed;
        @JsonProperty("wind_direction_10m")
        private Integer windDirection;
        @JsonProperty("weather_code")
        private Integer weatherCode;
        @JsonProperty("cloud_cover")
        private Integer cloudCover;
        private Integer rain;
    }
}
