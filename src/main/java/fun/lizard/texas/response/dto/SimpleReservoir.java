package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleReservoir {

    private double lat;
    private double lon;
    private String name;
    private double percentFull;
    private String asOfDate;
    private String nearestCity;
}
