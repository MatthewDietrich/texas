package fun.lizard.texas.response.openmeteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OpenMeteoHistoricalResponse {

    private Double latitude;
    private Double longitude;
    private Daily daily;

    @Data
    @NoArgsConstructor
    public static class Daily {
        @JsonProperty("weather_code")
        private List<Integer> weatherCode;
        @JsonProperty("temperature_2m_max")
        private List<Double> maxTemperature;
        @JsonProperty("temperature_2m_min")
        private List<Double> minTemperature;
        private List<LocalDateTime> sunrise;
        private List<LocalDateTime> sunset;
        @JsonProperty("precipitation_sum")
        private List<Double> precipitationSum;
        @JsonProperty("wind_speed_10m_mean")
        private List<Double> meanWindSpeed;
        @JsonProperty("wind_direction_10m_dominant")
        private List<Integer> dominantWindDirection;
        @JsonProperty("relative_humidity_2m_mean")
        private List<Double> humidity;
        @JsonProperty("cloud_cover_mean")
        private List<Double> meanCloudCover;
        @JsonProperty("pressure_msl_mean")
        private List<Integer> pressureMsl;
    }
}
