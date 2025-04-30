package fun.lizard.texas.response.txdot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoadwayCctvStatus {

    private String dirDescription;
    private Integer displayOrder;
    private Integer distance;
    private Boolean hasSnapshot;
    @JsonProperty("icd_Id")
    private String icdId;
    private Double latitude;
    private Double longitude;
    private String latString;
    private String lonString;
    private String name;
    private String netId;
    private Integer roadwayOrder;
    private String statusDescription;
    private EquipLoc equipLoc;
}
