package fun.lizard.texas.response.waterdatafortexas;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecentConditionsResponse {

    private String type;
    private List<RecentConditionsFeature> features;
}
