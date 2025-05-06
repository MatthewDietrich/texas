package fun.lizard.texas.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleCity {

    private String name;
    private String countyName;
    private Double latitude;
    private Double longitude;
}
