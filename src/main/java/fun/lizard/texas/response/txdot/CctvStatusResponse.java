package fun.lizard.texas.response.txdot;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class CctvStatusResponse {

    private List<CctvStatusRoadway> cctvStatusRoadways;
    private Map<String, List<RoadwayCctvStatus>> roadwayCctvStatuses;
}
