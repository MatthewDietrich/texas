package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherAlert {

    private String event;
    private String severity;
    private String certainty;
    private String urgency;
    private String endTime;
}
