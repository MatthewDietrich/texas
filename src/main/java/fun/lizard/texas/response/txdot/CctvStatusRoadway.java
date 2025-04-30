package fun.lizard.texas.response.txdot;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CctvStatusRoadway {

    private String name;
    private String direction;
    private String directionDescription;
}
