package fun.lizard.texas.response.txdot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CctvSnapshotResponse {

    @JsonProperty("icd_Id")
    private String icdId;
    private byte[] snippet;
    private String timestampFormatted;
}
