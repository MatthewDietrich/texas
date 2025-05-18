package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WeatherForecastResponse {

    private Double latitude;
    private Double longitude;
    private List<DailyForecast> dailyForecasts;
    private List<HourlyForecast> hourlyForecasts;
    private Current current;
}
