package fun.lizard.texas.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Airport {

    @Id
    private String _id;
    private String type;
    private Integer id;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint geometry;
    private Properties properties;

    @Data
    @NoArgsConstructor
    public static class Properties {
        private Integer GID;
        private String ARPRT_NM;
        private String CSTMS_FLAG;
        private String FAA_CD;
        private Integer CNTY_NBR;
        private Integer DIST_NBR;
        private String DISPLAY_FLAG;
        private Integer OBJECTID;
    }
}
