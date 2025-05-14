package fun.lizard.texas.response;

import fun.lizard.texas.response.openmeteo.Current;
import fun.lizard.texas.response.openmeteo.Forecast;
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
