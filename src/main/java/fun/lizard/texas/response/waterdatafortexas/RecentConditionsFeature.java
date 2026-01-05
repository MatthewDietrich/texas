package fun.lizard.texas.response.waterdatafortexas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class RecentConditionsFeature {

    private String type;
    private Geometry geometry;
    private Properties properties;

    @Data
    @NoArgsConstructor
    public static class Geometry {

        private List<Double> coordinates;
        private String type;
    }

    @Data
    @NoArgsConstructor
    public static class Properties {

        private Double area;

        @JsonProperty("condensed_name")
        private String condensedName;

        @JsonProperty("conservation_capacity")
        private Integer conservationCapacity;

        @JsonProperty("conservation_pool_elevation")
        private Double conservationPoolElevation;

        @JsonProperty("conservation_storage")
        private Integer conservationStorage;

        @JsonProperty("dead_pool_elevation")
        private Double deadPoolElevation;

        private Double elevation;

        @JsonProperty("flood_control_lake")
        private String floodControlLake;

        @JsonProperty("full_name")
        private String fullName;

        @JsonProperty("percent_full")
        private Double percentFull;

        @JsonProperty("short_name")
        private String shortName;

        private List<String> tags;

        private LocalDate timestamp;

        private Integer volume;

        @JsonProperty("volume_under_conservation_pool_elevation")
        private Integer volumeUnderConservationPoolElevation;
    }
}
