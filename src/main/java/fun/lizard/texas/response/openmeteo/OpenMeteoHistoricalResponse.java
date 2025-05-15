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
    public class Daily {
        @JsonProperty("weather_code")
        List<Integer> weatherCode;
        @JsonProperty("temperature_2m_max")
        List<Double> maxTemperature;
        @JsonProperty("temperature_2m_min")
        List<Double> minTemperature;
        List<LocalDateTime> sunrise;
        List<LocalDateTime> sunset;
        @JsonProperty("precipitation_sum")
        List<Double> precipitationSum;
        @JsonProperty("wind_speed_10m_mean")
        List<Double> meanWindSpeed;
        @JsonProperty("wind_direction_10m_dominant")
        List<Integer> dominantWindDirection;
        @JsonProperty("relative_humidity_2m_mean")
        List<Double> humidity;
        @JsonProperty("cloud_cover_mean")
        List<Double> meanCloudCover;
    }
}
