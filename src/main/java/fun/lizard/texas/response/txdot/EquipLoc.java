package fun.lizard.texas.response.txdot;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EquipLoc {

    private String direction;
    private String locationDescription;
    private String roadway;
    private String roadwayWithoutHyphensOrSpaces;
}
