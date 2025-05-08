package fun.lizard.texas.response.weather;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WeatherResponse {

    private Double latitude;
    private Double longitude;
    private List<Forecast> forecasts;
    private Current current;
}
