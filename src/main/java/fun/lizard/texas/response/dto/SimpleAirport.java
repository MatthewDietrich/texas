package fun.lizard.texas.response.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleAirport {

    private String name;
    private String cityName;
    private String code;
}
