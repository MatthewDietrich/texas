package fun.lizard.texas.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class District {

    @Id
    private String _id;
    private String type;
    private Integer id;
    private DistrictProperties properties;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPolygon geometry;

    @Data
    @NoArgsConstructor
    public static class DistrictProperties {

        private Integer OBJECTID;
        private String TXDOT_DIST_ABRVN_NM;
        private Integer TXDOT_DIST_NBR;
        @Indexed
        private String TXDOT_DIST_NM;
        private String TYPE;
        private Integer DIST_NBR;
        private String DIST_NM;
        private String DIST_ABRVN;
    }
}
