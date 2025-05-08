package fun.lizard.texas.response.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class OpenMeteoResponse {

    private Double latitude;
    private Double longitude;
    private Current current;
    private Daily daily;

    @Data
    @NoArgsConstructor
    public static class Current {

        @JsonProperty("temperature_2m")
        private Double temperature;
        @JsonProperty("relative_humidity_2m")
        private Integer relativeHumidity;
        @JsonProperty("apparent_temperature")
        private Double apparentTemperature;
        @JsonProperty("precipitation_probability")
        private Integer precipitationProbability;
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

    @Data
    @NoArgsConstructor
    public static class Daily {
        List<LocalDate> time;
        @JsonProperty("weather_code")
        List<Integer> weatherCode;
        @JsonProperty("temperature_2m_max")
        List<Double> maxTemperature;
        @JsonProperty("temperature_2m_min")
        List<Double> minTemperature;
        @JsonProperty("precipitation_probability_max")
        List<Integer> precipitationChance;
    }
}
