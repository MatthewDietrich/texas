package fun.lizard.texas.service;

import fun.lizard.texas.entity.City;
import fun.lizard.texas.entity.txdot.Camera;
import fun.lizard.texas.feign.TxdotFeignClient;
import fun.lizard.texas.repository.CameraRepository;
import fun.lizard.texas.repository.CityRepository;
import fun.lizard.texas.response.txdot.CctvSnapshotResponse;
import fun.lizard.texas.response.txdot.CctvStatusResponse;
import org.locationtech.jts.geom.GeometryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Limit;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CctvService {

    private static final Logger log = LoggerFactory.getLogger(CctvService.class);
    @Autowired
    TxdotFeignClient txdotFeignClient;

    @Autowired
    CameraRepository cameraRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${app.check-distance}")
    Double distanceToCheck;

    @Value("${app.city-camera-limit}")
    Integer cameraLimit;

    GeometryFactory geometryFactory = new GeometryFactory();

    public List<Camera> getCamerasByDistrictId(String districtId) {
        List<Camera> cameras = new ArrayList<>();
        CctvStatusResponse cctvStatusResponse = txdotFeignClient.getCctvStatusListByDistrict(districtId);
        cctvStatusResponse.getRoadwayCctvStatuses().forEach((roadwayName, cctvStatuses) -> cctvStatuses.forEach((roadwayCctvStatus -> {
            Camera camera = new Camera();
            camera.setLocation(new GeoJsonPoint(new Point(roadwayCctvStatus.getLongitude(), roadwayCctvStatus.getLatitude())));
            camera.setIcdId(roadwayCctvStatus.getIcdId());
            camera.setDirection(roadwayCctvStatus.getDirDescription());
            camera.setHasSnapshot(roadwayCctvStatus.getHasSnapshot());
            camera.setDistrictAbbreviation(districtId);
            Query query = new Query(Criteria.where("icdId").is(camera.getIcdId()));
            cameras.add(mongoTemplate.update(Camera.class)
                    .matching(query)
                    .replaceWith(camera)
                    .withOptions(FindAndReplaceOptions.options().upsert().returnNew())
                    .as(Camera.class)
                    .findAndReplace()
                    .get());
        })));
        return cameras;
    }

    public List<Camera> getCamerasByCityName(String cityName) {
        City city = cityRepository.findOneByName(cityName);
        if (null == city) {
            return List.of();
        }
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        Point point = new Point(longitude, latitude);
        return cameraRepository.findByLocationNear(point, new Distance(0.4), Limit.of(cameraLimit));
    }

    public List<CctvSnapshotResponse> getSnapshotsByCityName(String cityName) {
        City city = cityRepository.findOneByName(cityName);
        if (null == city) {
            log.info("City not found: {}", cityName);
            return null;
        }
        double latitude = Double.parseDouble(city.getProperties().getIntptlat());
        double longitude = Double.parseDouble(city.getProperties().getIntptlon());
        Point point = new Point(longitude, latitude);
        List<Camera> cameras = cameraRepository.findByLocationNear(point, new Distance(0.4), Limit.of(cameraLimit));
        return cameras.stream().map(camera -> txdotFeignClient.getCctvSnapshotByIcdId(camera.getIcdId(), camera.getDistrictAbbreviation())).toList();
    }
}
