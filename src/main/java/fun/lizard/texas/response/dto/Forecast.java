package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Forecast {

    private String iconClass;
    private String description;
    private LocalDate date;
    private String shortWeekday;
    private Integer precipitationChance;
    private Integer highTemperature;
    private Integer lowTemperature;
}
