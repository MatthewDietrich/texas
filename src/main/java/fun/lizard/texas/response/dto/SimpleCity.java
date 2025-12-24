package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SimpleCity {

    private String name;
    private String countyName;
    private Double latitude;
    private Double longitude;
    private Integer timesSearched;
    private LocalDateTime lastSearched;
    private String population;
    private List<String> nearestThreeNames;
}
