package fun.lizard.texas.response.openmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OpenMeteoForecastResponse {

    private Double latitude;
    private Double longitude;
    private Current current;
    private Daily daily;
    private Hourly hourly;

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
        @JsonProperty("is_day")
        private Integer isDay;
        @JsonProperty("pressure_msl")
        private Integer pressureMsl;
    }

    @Data
    @NoArgsConstructor
    public static class Daily {
        private List<LocalDate> time;
        @JsonProperty("weather_code")
        private List<Integer> weatherCode;
        @JsonProperty("temperature_2m_max")
        private List<Double> maxTemperature;
        @JsonProperty("temperature_2m_min")
        private List<Double> minTemperature;
        @JsonProperty("precipitation_probability_max")
        private List<Integer> precipitationChance;
    }

    @Data
    @NoArgsConstructor
    public static class Hourly {
        private List<LocalDateTime> time;
        @JsonProperty("weather_code")
        private List<Integer> weatherCode;
        @JsonProperty("temperature_2m")
        private List<Double> temperature;
        @JsonProperty("precipitation_probability")
        private List<Integer> precipitationChance;
        @JsonProperty("is_day")
        private List<Integer> isDay;
    }
}
