package fun.lizard.texas.response.nws;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NwsAlertFeature {

    private String type;
    private GeoJsonPolygon geometry;
    private Properties properties;

    @Data
    @NoArgsConstructor
    public static class Properties {

        private String id;
        private String areaDesc;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime sent;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime effective;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime onset;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime expires;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime ends;
        private String status;
        private String messageType;
        private String severity;
        private String certainty;
        private String urgency;
        private String event;
    }
}
