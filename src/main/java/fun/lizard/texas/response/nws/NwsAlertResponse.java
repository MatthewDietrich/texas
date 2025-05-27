package fun.lizard.texas.response.nws;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NwsAlertResponse {

    private String type;
    private List<NwsAlertFeature> features;
}
