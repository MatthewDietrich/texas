package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class HourlyForecast {

    private LocalDateTime time;
    private String timeFormatted;
    private Integer temperature;
    private Integer precipitationChance;
    private String description;
    private String iconClass;
}
