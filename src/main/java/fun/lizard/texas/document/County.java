package fun.lizard.texas.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class County {

    @Id
    private String id;
    private String type;
    private CountyProperties properties;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonMultiPolygon geometry;

    @Data
    @NoArgsConstructor
    public static class CountyProperties {

        private Integer gid;
        private String statefp;
        private String countyfp;
        private String countyns;
        private String geoid;
        @Indexed
        private String name;
        private String namelsad;
        private String lsad;
        private String classfp;
        private String csafp;
        private String cbsafp;
        private String metdivfp;
        private String funcstat;
        private Long aland;
        private Long awater;
        private String intptlat;
        private String intptlon;
    }
}
