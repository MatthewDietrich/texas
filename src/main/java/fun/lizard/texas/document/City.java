package fun.lizard.texas.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document
public class City {

    @Id
    private String id;
    private String type;
    private CityProperties properties;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonMultiPolygon geometry;
    private LocalDateTime lastSearched;
    private Integer timesSearched;
    private Integer population;

    @Data
    @NoArgsConstructor
    public static class CityProperties {

        private Integer gid;
        private String statefp;
        private String placefp;
        private String placens;
        private String geoid;
        @Indexed
        private String name;
        private String namelsad;
        private String lsad;
        private String classfp;
        private String pcicbsa;
        private String pcinecta;
        private String mtfcc;
        private String funcstat;
        private Long aland;
        private Long awater;
        private String intptlat;
        private String intptlon;
    }
}
