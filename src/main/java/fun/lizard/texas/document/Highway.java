package fun.lizard.texas.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document
public class Highway {

    private String _id;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonLineString geometry;
    private String name;
}
